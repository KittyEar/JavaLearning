package com.gameserver.exceptions.gameException.item;

import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ItemNumErrorException extends CustomBaseException {
    public ItemNumErrorException() {
        super(HttpStatus.ITEM_NUM_ERROR.getCode(), HttpStatus.ITEM_NUM_ERROR.getMsg());
    }
}
