package com.gameserver.exceptions.gameException.item;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ItemNotEnoughErrorException extends CustomBaseException {
    public ItemNotEnoughErrorException() {
        super(HttpStatus.ITEM_NOT_ENOUGH_ERROR.getCode(), HttpStatus.ITEM_NOT_ENOUGH_ERROR.getMsg());
    }
}
