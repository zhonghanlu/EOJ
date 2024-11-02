package com.emode.eoj.model.request;

import lombok.Data;

@Data
public class EProblemSubmitRequest {

    private String language;

    private String code;

    private Long problemId;

}
