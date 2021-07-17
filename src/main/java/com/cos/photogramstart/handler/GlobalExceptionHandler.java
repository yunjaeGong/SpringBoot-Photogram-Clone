package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.handler.exception.CustomException;
import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.handler.exception.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomValidationException.class)
    public String handleValidationException(CustomValidationException e) {
        System.out.println("handleValidationException");
        // 오류를 alert로 보이고, history.back()을 통해 화면 넘어감(error page)을 방지
        // client(browser) 응답에는 script가 좋음
        // ajax, android(개발자) 통신에는 RespDTO 처리가 편리.
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }


    @ExceptionHandler(value = CustomValidationApiException.class)
    public ResponseEntity<?> handleValidationApiException(CustomValidationApiException e) {
        System.out.println("handleValidationException");
        return new ResponseEntity<CMRespDto<?>>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CustomApiException.class)
    public ResponseEntity<?> handleApiException(CustomApiException e) {
        System.out.println("handleApiException");
        return new ResponseEntity<CMRespDto<?>>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CustomException.class)
    public String handleApiException(CustomException e) {
        return Script.back(e.getMessage());
    }
}
