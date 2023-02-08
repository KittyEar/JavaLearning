package com.gameserver.exceptions.gameException.tavern;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class TavernFormatErrorException extends CustomBaseException {
    public TavernFormatErrorException() {
        super(HttpStatus.TAVERN_FORMAT_ERROR.getCode(), HttpStatus.TAVERN_FORMAT_ERROR.getMsg());
    }
}
