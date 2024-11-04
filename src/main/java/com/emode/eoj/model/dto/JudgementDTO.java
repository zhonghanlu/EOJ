
package com.emode.eoj.model.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class JudgementDTO {

    private Double score;

    private List<TestCaseResult> testCaseResultList;

    @Data
    public static class TestCaseResult {
        private String inputCase;  // 用例输入
        private String outputCase; // 用户输出
        private boolean result;
    }

}
