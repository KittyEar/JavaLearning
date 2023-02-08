package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;


public class HeroCreateErrorException extends CustomBaseException {
    public HeroCreateErrorException() {
        super(HttpStatus.HERO_CREATE_ERROR.getCode(), HttpStatus.HERO_CREATE_ERROR.getMsg());
    }
}
