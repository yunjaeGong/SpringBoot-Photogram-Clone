package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.handler.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {
    // advice - 공통기능(AOP)
    // Validation 필요한 메소드들 (Binding Result)의 Validation 처리 여기서

    @Around(value = "execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 해당 경로의 함수가 실행되면 이 메소드가 우선 실행
        // proceedingJoinPoint => 해당 함수의 모든 곳에 접근 할 수 있는 변수

        System.out.println("========== Web Api Controller ==========");
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed(); // 실행될 함수로 복귀
    }

    @Around(value = "execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("========== Web Controller ==========");
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
