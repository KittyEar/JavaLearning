package com.gameserver.exceptions.gameException.item;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ItemCreateErrorException extends CustomBaseException {
    public ItemCreateErrorException() {
        super(HttpStatus.ITEM_CREATE_ERROR.getCode(), HttpStatus.ITEM_CREATE_ERROR.getMsg());
    }
}
