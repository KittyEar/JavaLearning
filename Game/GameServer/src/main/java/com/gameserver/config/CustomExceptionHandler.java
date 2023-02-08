package com.gameserver.config;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.SRet;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {
    @ExceptionHandler(CustomBaseException.class)
    public SRet<?> handleHttpMessageNotReadableException(CustomBaseException ex) {
        // 直接向客户端返回消息
        return SRet.error(ex.getCode(), ex.getMsg());
    }
}