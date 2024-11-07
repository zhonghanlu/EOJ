package com.emode.eoj.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EProblemAnswerKeyRequest {

    private Long problemId;

    private String content;

    private String answerKey;

}
