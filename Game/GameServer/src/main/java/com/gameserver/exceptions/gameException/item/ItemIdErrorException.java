package com.gameserver.exceptions.gameException.item;


import com.gameserver.exceptions.CustomBaseException;
import com.gameserver.pojo.msg.HttpStatus;

public class ItemIdErrorException extends CustomBaseException {
    public ItemIdErrorException() {
        super(HttpStatus.ITEM_ID_ERROR.getCode(), HttpStatus.ITEM_ID_ERROR.getMsg());
    }
}
