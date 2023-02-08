package com.gameserver.exceptions.gameException;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class TokenErrorException extends CustomBaseException {
    public TokenErrorException() {
        super(HttpStatus.TOKEN_ERROR.getCode(), HttpStatus.TOKEN_ERROR.getMsg());
    }
}
