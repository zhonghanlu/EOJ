package com.emode.eoj.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emode.eoj.judge.JudgeRun;
import com.emode.eoj.judge.param.JudgeContext;
import com.emode.eoj.mapper.ECaseMapper;
import com.emode.eoj.mapper.EPSubmitMapper;
import com.emode.eoj.mapper.EProblemMapper;
import com.emode.eoj.model.dto.JudgementDTO;
import com.emode.eoj.model.request.EProblemAnswerKeyRequest;
import com.emode.eoj.model.request.EProblemSubmitRequest;
import com.emode.eoj.pojo.ECase;
import com.emode.eoj.pojo.EPSubmit;
import com.emode.eoj.pojo.EProblem;
import com.emode.eoj.service.IEProblemService;
import com.emode.eoj.util.webmvc.EOJServiceException;
import com.emode.eoj.util.webmvc.IDGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author zhl
 * @since 2024-11-02
 */
@Service
@RequiredArgsConstructor
public class EProblemServiceImpl extends ServiceImpl<EProblemMapper, EProblem> implements IEProblemService {

    private final EProblemMapper problemMapper;

    private final ECaseMapper eCaseMapper;

    private final EPSubmitMapper epSubmitMapper;

    private final JudgeRun judgeRun;

    @Override
    public JudgementDTO submit(EProblemSubmitRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();
        Long problemId = request.getProblemId();
        // 1.判断题目是否存在
        EProblem eProblem = problemMapper.selectById(problemId);
        if (Objects.isNull(eProblem)) {
            throw new EOJServiceException("当前问题不存在");
        }
        // 2.判断当前测试用例是否充分
        LambdaQueryWrapper<ECase> wrapper = Wrappers.lambdaQuery(ECase.class);
        wrapper.eq(ECase::getProblemId, problemId);
        List<ECase> eCaseList = eCaseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(eCaseList)) {
            throw new EOJServiceException("测试用例不充分，请联系老师测评");
        }
        // 3.入参语言是否合法
        List<String> languageList = Arrays.asList("JAVA", "C++", "PYTHON");
        String languageUpperCase = language.toUpperCase();
        if (!languageList.contains(languageUpperCase)) {
            throw new EOJServiceException("当前语言类型暂不支持");
        }
        // 4.入参代码是否合规
        boolean codeFlag = checkCodeRule(code);
        if (!codeFlag) {
            throw new EOJServiceException("当前代码不符合规范，请重新输入");
        }
        // 5.入库 新增题目提交记录
        EPSubmit epSubmit = new EPSubmit();
        epSubmit.setId(IDGenerator.next());
        epSubmit.setProblemId(problemId);
        epSubmit.setStatus("TO_JUDGE");
        epSubmit.setCodeInput(code);
        epSubmit.setLanguage(request.getLanguage());
        int a = epSubmitMapper.insert(epSubmit);

        if (a < 1) {
            throw new EOJServiceException("题目提交失败");
        }
        // 6.调用判题机处理
        JudgeContext judgeContext = JudgeContext.builder().language(request.getLanguage())
                .code(request.getCode())
                .eProblem(eProblem)
                .epSubmitId(epSubmit.getId()).build();
        List<String> outCaseList = judgeRun.run(judgeContext);

        // 7.调用判题服务，检查测试用例与结果是否一致
        JudgementDTO judgementDTO = judgement(problemId, outCaseList);
        epSubmit.setStatus("JUDGE_SUCCESS");
        epSubmit.setScore(String.valueOf(judgementDTO.getScore()));
        epSubmit.setCodeOutput(JSON.toJSONString(judgementDTO));

        int i = epSubmitMapper.updateById(epSubmit);

        if (i <= 0) {
            throw new EOJServiceException("题目提交回执结果更新失败");
        }
        return judgementDTO;
    }

    @Override
    public void writeAnswerKey(EProblemAnswerKeyRequest request) {
        EProblem eProblem = problemMapper.selectById(request.getProblemId());

        if (Objects.isNull(eProblem)) {
            throw new EOJServiceException("题目不存在，不可进行编辑");
        }

        eProblem.setAnswerKey(request.getAnswerKey());

        int i = problemMapper.updateById(eProblem);

        if (i <= 0) {
            throw new EOJServiceException("编辑错误");
        }
    }

    /**
     * 根据判题机返回的结果 与测试用例进行比对
     */
    private JudgementDTO judgement(long problemId, List<String> outCaseList) {
        List<JudgementDTO.TestCaseResult> testCaseResultList = new ArrayList<>();

        LambdaQueryWrapper<ECase> wrapper = Wrappers.lambdaQuery(ECase.class);
        wrapper.eq(ECase::getProblemId, problemId);
        List<ECase> eCaseList = eCaseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(outCaseList) || (eCaseList.size() != outCaseList.size())) {
            throw new EOJServiceException("判题机执行有误，测试用例执行不全面");
        }

        // 比对用例
        for (int i = 0; i < eCaseList.size(); i++) {
            boolean flag = Boolean.FALSE;
            if (eCaseList.get(i).getOutputCase().equals(outCaseList.get(i))) {
                flag = Boolean.TRUE;
            }
            JudgementDTO.TestCaseResult caseResult = new JudgementDTO.TestCaseResult();
            caseResult.setInputCase(eCaseList.get(i).getInputCase());
            caseResult.setOutputCase(outCaseList.get(i));
            caseResult.setResult(flag);
            testCaseResultList.add(caseResult);
        }

        // 计算得分 根据 100 分 算正确的测试用例
        BigDecimal total = new BigDecimal(100);
        BigDecimal size = new BigDecimal(eCaseList.size());
        BigDecimal singleScore = total.divide(size, 2, RoundingMode.HALF_UP);
        long trueSize = testCaseResultList.stream().filter(JudgementDTO.TestCaseResult::isResult).count();

        // 总分
        BigDecimal resultScore;
        if (size.longValue() <= trueSize) {
            resultScore = new BigDecimal(100);
            return JudgementDTO.builder().score(resultScore.doubleValue())
                    .testCaseResultList(testCaseResultList)
                    .build();
        }

        resultScore = singleScore.multiply(new BigDecimal(trueSize))
                .setScale(2, RoundingMode.HALF_UP);

        return JudgementDTO.builder().score(resultScore.doubleValue())
                .testCaseResultList(testCaseResultList)
                .build();
    }

    private boolean checkCodeRule(String code) {
        return Boolean.TRUE;
    }
}
