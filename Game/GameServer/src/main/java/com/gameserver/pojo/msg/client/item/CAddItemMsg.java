package com.gameserver.pojo.msg.client.item;

import lombok.Data;

@Data
public class CAddItemMsg {
    private Integer itemTypeId;
    private Integer num;
}
