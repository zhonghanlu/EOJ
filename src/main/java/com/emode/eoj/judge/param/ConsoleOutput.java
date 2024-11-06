package com.emode.eoj.judge.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConsoleOutput {

    private String content;

    @Data
    public static class StreamInfo {
        private String name;
        private int max;
    }
}


