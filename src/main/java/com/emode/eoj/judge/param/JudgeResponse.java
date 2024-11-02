package com.emode.eoj.judge.param;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JudgeResponse {

    private String status;

    private String error;

    private String exitStatus;

    private long time;

    private long memory;

    private long runTime;

    private Files files;

    private Map<String, String> fileIds;

    private List<String> fileError;


    @Data
    public static class Files {
        private String stderr;
        private String stdout;
    }

}
