package com.emode.eoj.judge.param;

import com.emode.eoj.pojo.EProblem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 负责判题业务上下文参数传递
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JudgeContext {

    private String language;

    private String code;

    private EProblem eProblem;

    private JudgeParam judgeParam;

}
