package com.gameserver.exceptions.gameException.hero;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroStarStoneNotEnoughErrorException extends CustomBaseException {
    public HeroStarStoneNotEnoughErrorException() {
        super(HttpStatus.HERO_STAR_STONE_NOT_ENOUGH_ERROR.getCode(), HttpStatus.HERO_STAR_STONE_NOT_ENOUGH_ERROR.getMsg());
    }
}
