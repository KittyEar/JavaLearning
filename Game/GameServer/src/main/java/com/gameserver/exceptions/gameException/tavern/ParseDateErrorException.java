package com.gameserver.exceptions.gameException.tavern;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ParseDateErrorException extends CustomBaseException {
    public ParseDateErrorException() {
        super(HttpStatus.TAVERN_FORMAT_ERROR.getCode(), HttpStatus.TAVERN_FORMAT_ERROR.getMsg());
    }
}
