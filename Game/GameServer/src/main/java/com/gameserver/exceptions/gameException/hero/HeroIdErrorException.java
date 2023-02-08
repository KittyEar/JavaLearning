package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroIdErrorException extends CustomBaseException {
    public HeroIdErrorException() {
        super(HttpStatus.HERO_ID_ERROR.getCode(), HttpStatus.HERO_ID_ERROR.getMsg());
    }
}
