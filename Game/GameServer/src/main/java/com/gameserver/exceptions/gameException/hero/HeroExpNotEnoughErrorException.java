package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;


public class HeroExpNotEnoughErrorException extends CustomBaseException {
    public HeroExpNotEnoughErrorException() {
        super(HttpStatus.HERO_EXP_NOT_ENOUGH_ERROR.getCode(), HttpStatus.HERO_EXP_NOT_ENOUGH_ERROR.getMsg());
    }
}
