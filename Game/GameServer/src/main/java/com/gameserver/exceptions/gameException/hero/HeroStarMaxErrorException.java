package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroStarMaxErrorException extends CustomBaseException {
    public HeroStarMaxErrorException() {
        super(HttpStatus.HERO_STAR_MAX_ERROR.getCode(), HttpStatus.HERO_STAR_MAX_ERROR.getMsg());
    }
}
