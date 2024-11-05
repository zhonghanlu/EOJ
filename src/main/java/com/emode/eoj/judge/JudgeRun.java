package com.emode.eoj.judge;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.emode.eoj.judge.param.JudgeContext;
import com.emode.eoj.judge.param.JudgeParam;
import com.emode.eoj.judge.param.JudgeResponse;
import com.emode.eoj.pojo.ECase;
import com.emode.eoj.pojo.ELanguage;
import com.emode.eoj.pojo.EProblem;
import com.emode.eoj.service.IECaseService;
import com.emode.eoj.service.IELanguageService;
import com.emode.eoj.util.http.HttpUtils;
import com.emode.eoj.util.webmvc.EOJServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JudgeRun {

    private final IELanguageService languageService;

    private final IECaseService caseService;

    @Value("${goJudge.url}")
    private String goJudgeUrl;

    /**
     * 编译 -》 运行 -》 删除
     */
    public List<String> run(JudgeContext judgeContext) {
        // 编译
        String execId = compile(judgeContext);
        // 执行代码
        List<String> outCaseList = exec(judgeContext, execId);
        // 删除执行文件
        delete(execId);
        return outCaseList;
    }


    /**
     * 编译
     */
    private String compile(JudgeContext judgeContext) {
        JudgeParam judgeParam = buildCompileParam(judgeContext);
        ELanguage eLanguage = getELanguage(judgeContext);

        Map<String, List<JudgeParam>> paramMap = new HashMap<>();
        paramMap.put("cmd", Collections.singletonList(judgeParam));
        String compileRequest = JSON.toJSONString(paramMap);
        log.info("compile request : {}", compileRequest);

        String result = HttpUtils.doPost(goJudgeUrl + "/run", compileRequest);

        List<JudgeResponse> judgeResponseList = JSON.parseArray(result, JudgeResponse.class);
        JudgeResponse judgeResponse = judgeResponseList.get(0);
        log.info("compiling response : {} ", judgeResponse);

        if (StringUtils.isNoneBlank(judgeResponse.getError())) {
            log.info("compile error : {}", judgeResponse.getError());
            throw new EOJServiceException("编译失败");
        }

        if ("Accepted".equals(judgeResponse.getStatus()) && "0".equals(judgeResponse.getExitStatus())) {
            log.info("compile successfully");
        }

        Map<String, String> fileIds = judgeResponse.getFileIds();
        return fileIds.get(eLanguage.getCopyOutCached());
    }

    /**
     * 执行
     */
    private List<String> exec(JudgeContext judgeContext, String execId) {
        EProblem eProblem = judgeContext.getEProblem();
        List<JudgeParam> judgeParamList = new ArrayList<>();
        Map<String, List<JudgeParam>> cmdMap = new HashMap<>();

        LambdaQueryWrapper<ECase> wrapper = Wrappers.lambdaQuery(ECase.class);
        wrapper.eq(ECase::getProblemId, eProblem.getId());
        List<ECase> eCaseList = caseService.list(wrapper);

        eCaseList.forEach(eCase -> {
            JudgeParam judgeParam = buildRunParam(judgeContext, execId, eCase.getInputCase());
            judgeParamList.add(judgeParam);
        });

        cmdMap.put("cmd", judgeParamList);

        String runRequest = JSON.toJSONString(cmdMap);
        log.info("run exec request : {}", runRequest);

        String runResponse = HttpUtils.doPost(goJudgeUrl + "/run", runRequest);

        List<JudgeResponse> judgeResponseList = JSON.parseArray(runResponse, JudgeResponse.class);

        return judgeResponseList.stream()
                .map(JudgeResponse::getFiles)
                .collect(Collectors.toList())
                .stream()
                .map(JudgeResponse.Files::getStdout)
                .collect(Collectors.toList());
    }

    /**
     * 删除执行文件
     */
    private void delete(String execId) {

        HttpUtils.doDelete(goJudgeUrl + "/file/:" + execId);

    }

    /**
     * 根据各种语言构建编译参数
     */
    private JudgeParam buildCompileParam(JudgeContext judgeContext) {
        ELanguage languageInfo = getELanguage(judgeContext);

        if (Objects.isNull(languageInfo)) {
            throw new EOJServiceException("当前语言类型不存在");
        }

        Map<String, Map<String, String>> copyIn = new HashMap<>();

        Map<String, String> code = new HashMap<>();
        code.put("content", judgeContext.getCode());
        copyIn.put(languageInfo.getCompileCopyIn(), code);

        return JudgeParam.builder()
                .args(Arrays.asList(languageInfo.getCompileArgs().split(",")))
                .env(Arrays.asList(languageInfo.getCompileEnv().split(",")))
                .files(Arrays.asList("{\"content\": \"\"}", "{\"name\": \"stdout\", \"max\": 10240}", "{\"name\": \"stderr\", \"max\": 10240}"))
                .cpuLimit(1000000)
                .memoryLimit(1000000)
                .procLimit(1000000)
                .copyIn(copyIn)
                .copyOut(Arrays.asList("stdout", "stderr"))
                .copyOutCached(Arrays.asList(languageInfo.getCopyOutCached()))
                .build();
    }

    /**
     * 根据各种语言构建执行参数
     */
    private JudgeParam buildRunParam(JudgeContext judgeContext, String execId, String inputCase) {
        ELanguage languageInfo = getELanguage(judgeContext);

        if (Objects.isNull(languageInfo)) {
            throw new EOJServiceException("当前语言类型不存在");
        }

        Map<String, Map<String, String>> copyIn = new HashMap<>();

        Map<String, String> execCode = new HashMap<>();
        execCode.put("fileId", execId);
        copyIn.put(languageInfo.getRunCopyIn(), execCode);

        return JudgeParam.builder()
                .args(Arrays.asList(languageInfo.getRunArgs().split(",")))
                .env(Arrays.asList(languageInfo.getRunEnv().split(",")))
                .files(Arrays.asList("{\"content\": " + inputCase + "}", "{\"name\": \"stdout\", \"max\": 10240}", "{\"name\": \"stderr\", \"max\": 10240}"))
                .cpuLimit(1000000)
                .memoryLimit(1000000)
                .procLimit(1000000)
                .copyIn(copyIn)
                .build();
    }


    private ELanguage getELanguage(JudgeContext judgeContext) {
        String language = judgeContext.getLanguage();
        LambdaQueryWrapper<ELanguage> wrapper = Wrappers.lambdaQuery(ELanguage.class);
        wrapper.eq(ELanguage::getLanguage, language)
                .last("LIMIT 1");
        ELanguage languageInfo = languageService.getOne(wrapper);
        return languageInfo;
    }

}
