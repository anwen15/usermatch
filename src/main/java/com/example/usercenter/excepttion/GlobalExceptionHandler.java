package com.example.usercenter.excepttion;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.EoorCode;
import com.example.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionhandler(BusinessException e) {
        log.error("业务异常",e.getMessage());
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }
    @ExceptionHandler
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("系统异常",e);
        return ResultUtils.error(EoorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
