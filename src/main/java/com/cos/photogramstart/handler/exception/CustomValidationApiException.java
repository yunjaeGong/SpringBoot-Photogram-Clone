package com.cos.photogramstart.handler.exception;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

    // JVM이 객체를 구분할 때 사용
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationApiException(String message) {
        super(message);
    }

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message); // 부모 Class들로 전달
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
