package com.gameserver.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomBaseException extends RuntimeException{
    protected Integer code;
    protected String msg;

    public CustomBaseException(Integer code, String msg){
        this.msg = msg;
        this.code = code;
    }
}