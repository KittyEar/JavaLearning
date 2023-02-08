package com.gameserver.exceptions.gameException;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class NorErrorException extends CustomBaseException {
    public NorErrorException() {
        super(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg());
    }
}
