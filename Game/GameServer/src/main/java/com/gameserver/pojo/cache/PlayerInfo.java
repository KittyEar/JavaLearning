package com.gameserver.pojo.cache;

import com.gameserver.pojo.db.Hero;
import com.gameserver.pojo.db.Item;
import com.gameserver.pojo.db.Player;
import lombok.Data;

import java.util.List;

@Data
public class PlayerInfo {
    private Player baseProp;
    //一个玩家对应多个英雄
    // 英雄属性
    private List<Hero> heroes;
    // 道具属性
    private List<Item> items;

    private Long lastHeartBeatTime;
}
