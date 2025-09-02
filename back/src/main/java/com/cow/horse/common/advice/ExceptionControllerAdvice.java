package com.cow.horse.common.advice;


import com.cow.horse.common.constants.HttpStatus;
import com.cow.horse.common.exception.BusinessException;
import com.cow.horse.common.web.response.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理类的基类
 */
//@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public JsonResult<?> commonExceptionHandler(RuntimeException ex){
        ex.printStackTrace();
        return JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR,ex.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    public JsonResult<?> businessHanler(BusinessException ex){
        return JsonResult.error(HttpStatus.CODE_BUSINESS_ERROR,ex.getMessage());
    }


}
