package com.cos.photogramstart.handler.exception;

import java.util.Map;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message); // 부모 Class들로 전달
    }
}
