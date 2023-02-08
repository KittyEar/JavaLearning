package com.gameserver.exceptions.gameException;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class CoinNotEnoughErrorException extends CustomBaseException {
    public CoinNotEnoughErrorException() {
        super(HttpStatus.COIN_NOT_ENOUGH_ERROR.getCode(), HttpStatus.COIN_NOT_ENOUGH_ERROR.getMsg());
    }
}
