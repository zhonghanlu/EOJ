package com.emode.eoj.judge;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.emode.eoj.judge.param.ConsoleOutput;
import com.emode.eoj.judge.param.JudgeContext;
import com.emode.eoj.judge.param.JudgeParam;
import com.emode.eoj.judge.param.JudgeResponse;
import com.emode.eoj.pojo.ECase;
import com.emode.eoj.pojo.ELanguage;
import com.emode.eoj.pojo.EPSubmit;
import com.emode.eoj.pojo.EProblem;
import com.emode.eoj.service.IECaseService;
import com.emode.eoj.service.IELanguageService;
import com.emode.eoj.service.IEPSubmitService;
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

    private final IEPSubmitService submitService;

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
        Long epSubmitId = judgeContext.getEpSubmitId();
        EPSubmit epSubmit = submitService.getById(epSubmitId);

        JudgeParam judgeParam = buildCompileParam(judgeContext);
        ELanguage eLanguage = getELanguage(judgeContext);

        Map<String, List<JudgeParam>> paramMap = new HashMap<>();
        paramMap.put("cmd", Collections.singletonList(judgeParam));
        String compileRequest = JSON.toJSONString(paramMap);
        log.info("compile request : {}", compileRequest);

        String result = HttpUtil.post(goJudgeUrl + "/run", compileRequest);

        List<JudgeResponse> judgeResponseList = JSON.parseArray(result, JudgeResponse.class);
        JudgeResponse judgeResponse = judgeResponseList.get(0);
        log.info("compiling response : {} ", judgeResponse);

        if (!"0".equals(judgeResponse.getExitStatus())) {
            log.info("compile 1 error : {}", judgeResponse.getError());
            log.info("compile 2 error : {}", judgeResponse.getExitStatus());
            log.info("compile 3 error : {}", judgeResponse.getStatus());

            epSubmit.setScore(String.valueOf(0));
            epSubmit.setStatus("JUDGE_COMPILE_ERROR");
            String stderr = judgeResponse.getFiles().getStderr();
            epSubmit.setCodeOutput(stderr);
            submitService.updateById(epSubmit);

            throw new EOJServiceException("编译失败:" + stderr);
        }

        if ("Accepted".equals(judgeResponse.getStatus())) {
            epSubmit.setStatus("JUDGE_COMPILE_SUCCESS");
            submitService.updateById(epSubmit);
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

        String runResponse = HttpUtil.post(goJudgeUrl + "/run", runRequest);

        List<JudgeResponse> judgeResponseList = JSON.parseArray(runResponse, JudgeResponse.class);

        return judgeResponseList.stream()
                .map(JudgeResponse::getFiles)
                .collect(Collectors.toList())
                .stream()
                .map(e -> removeLastNewLine(e.getStdout()))
                .collect(Collectors.toList());
    }

    public static String removeLastNewLine(String input) {
        int lastIndex = input.lastIndexOf("\n");
        if (lastIndex >= 0) {
            return input.substring(0, lastIndex);
        } else {
            return input;
        }
    }


    /**
     * 删除执行文件
     */
    private void delete(String execId) {

        String res = HttpUtil.get(goJudgeUrl + "/file/" + execId);

        log.info("file delete back result: {}", res);

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

        List<Object> consoleOutput = getConsoleOutput("");

        return JudgeParam.builder()
                .args(Arrays.asList(languageInfo.getCompileArgs().split("@")))
                .env(Arrays.asList(languageInfo.getCompileEnv().split(",")))
                .files(consoleOutput)
                .cpuLimit(10000000000L)
                .memoryLimit(104857600L)
                .procLimit(50L)
                .copyIn(copyIn)
                .copyOut(Arrays.asList("stdout", "stderr"))
                .copyOutCached(Collections.singletonList(languageInfo.getCopyOutCached()))
                .build();
    }

    private static List<Object> getConsoleOutput(String content) {
        ConsoleOutput output = new ConsoleOutput();
        output.setContent(content);
        ConsoleOutput.StreamInfo streamInfo = new ConsoleOutput.StreamInfo();
        streamInfo.setName("stdout");
        streamInfo.setMax(10240);
        ConsoleOutput.StreamInfo streamInfo1 = new ConsoleOutput.StreamInfo();
        streamInfo1.setName("stderr");
        streamInfo1.setMax(10240);
        return new ArrayList<>(Arrays.asList(output, streamInfo, streamInfo1));
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

        List<Object> consoleOutput = getConsoleOutput(inputCase);

        return JudgeParam.builder()
                .args(Arrays.asList(languageInfo.getRunArgs().split(",")))
                .env(Arrays.asList(languageInfo.getRunEnv().split(",")))
                .files(consoleOutput)
                .cpuLimit(10000000000L)
                .memoryLimit(104857600L)
                .procLimit(50L)
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
