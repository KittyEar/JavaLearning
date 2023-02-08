package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroMouldIdErrorException extends CustomBaseException {
    public HeroMouldIdErrorException() {
        super(HttpStatus.HERO_MOULD_ID_ERROR.getCode(), HttpStatus.HERO_MOULD_ID_ERROR.getMsg());
    }
}
