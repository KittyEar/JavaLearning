package com.gameserver.exceptions.gameException;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class NotLoginErrorException extends CustomBaseException {
    public NotLoginErrorException() {
        super(HttpStatus.NOT_LOGIN_ERROR.getCode(), HttpStatus.NOT_LOGIN_ERROR.getMsg());
    }
}
