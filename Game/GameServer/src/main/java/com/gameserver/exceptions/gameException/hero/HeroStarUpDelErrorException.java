package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroStarUpDelErrorException extends CustomBaseException {
    public HeroStarUpDelErrorException() {
        super(HttpStatus.HERO_STAR_UP_DEL_ERROR.getCode(), HttpStatus.HERO_STAR_UP_DEL_ERROR.getMsg());
    }
}