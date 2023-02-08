package com.gameserver.exceptions.gameException.tavern;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class TavernItemNotEnoughErrorException extends CustomBaseException {
    public TavernItemNotEnoughErrorException() {
        super(HttpStatus.TAVERN_ITEM_NOT_ENOUGH_ERROR.getCode(), HttpStatus.TAVERN_ITEM_NOT_ENOUGH_ERROR.getMsg());
    }
}
