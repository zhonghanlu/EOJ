package com.emode.eoj.util.webmvc;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhl
 */
@Setter
@Getter
public final class EOJServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    public EOJServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public EOJServiceException(String message) {
        this.message = message;
    }

    public EOJServiceException(Integer code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }
}