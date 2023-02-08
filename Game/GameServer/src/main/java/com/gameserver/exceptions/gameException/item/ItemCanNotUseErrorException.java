package com.gameserver.exceptions.gameException.item;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ItemCanNotUseErrorException extends CustomBaseException {
    public ItemCanNotUseErrorException() {
        super(HttpStatus.ITEM_CANNOT_USE_ERROR.getCode(), HttpStatus.ITEM_CANNOT_USE_ERROR.getMsg());
    }
}
