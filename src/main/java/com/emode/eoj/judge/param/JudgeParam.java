package com.emode.eoj.judge.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JudgeParam {

    private List<String> args;

    private List<String> env;

    private Object files;

    private Long cpuLimit;

    private Long memoryLimit;

    private Long procLimit;

    private Map<String, Map<String, String>> copyIn;

    private List<String> copyOut;

    private List<String> copyOutCached;


    @Data
    public static class Files {
        private String name;
        private String max;
    }

}
