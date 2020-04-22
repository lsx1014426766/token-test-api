package com.balala.exception;

import com.balala.base.ApiResponse;
import com.balala.constant.ApiCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private ApiResponse handlerException(Exception e) {
        logger.error(e.getMessage(), e);
        return ApiResponse.error(ApiCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

}
