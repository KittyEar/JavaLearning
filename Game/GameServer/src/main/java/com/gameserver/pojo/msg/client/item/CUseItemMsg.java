package com.gameserver.pojo.msg.client.item;

import lombok.Data;

@Data
public class CUseItemMsg {
    // 道具唯一id
    private Integer itemId;
    // 道具数量
    private Integer num;
}
