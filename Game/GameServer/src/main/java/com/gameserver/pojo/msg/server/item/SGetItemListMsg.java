package com.gameserver.pojo.msg.server.item;


import com.gameserver.pojo.db.Item;
import lombok.Data;

import java.util.List;

@Data
public class SGetItemListMsg {
    private List<Item> itemList;
}
