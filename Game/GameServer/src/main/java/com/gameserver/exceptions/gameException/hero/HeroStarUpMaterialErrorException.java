package com.gameserver.exceptions.gameException.hero;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class HeroStarUpMaterialErrorException extends CustomBaseException {
    public HeroStarUpMaterialErrorException() {
        super(HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getCode(), HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getMsg());
    }
}