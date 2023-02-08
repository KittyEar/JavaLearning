package com.gameserver.exceptions.gameException.tavern;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class FreeLottyErrorException extends CustomBaseException {
    public FreeLottyErrorException() {
        super(HttpStatus.TAVERN_FREE_LOTTERY_TIME_ERROR.getCode(), HttpStatus.TAVERN_FREE_LOTTERY_TIME_ERROR.getMsg());
    }
}
