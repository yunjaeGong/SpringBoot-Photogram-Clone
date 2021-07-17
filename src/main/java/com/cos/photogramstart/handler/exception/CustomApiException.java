package com.cos.photogramstart.handler.exception;

public class CustomApiException extends RuntimeException{

    // JVM이 객체를 구분할 때 사용
    private static final long serialVersionUID = 1L;

    public CustomApiException(String message) {
        super(message);
    }

}
