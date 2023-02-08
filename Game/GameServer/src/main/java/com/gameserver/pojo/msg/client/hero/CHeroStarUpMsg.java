package com.gameserver.pojo.msg.client.hero;

import lombok.Data;

@Data
public class CHeroStarUpMsg {
    private Integer heroId;
    // 相同的英雄
    private Integer consumeHeroId1;
    // 不同的英雄1
    private Integer consumeHeroId2;
    // 不同的英雄2
    private Integer consumeHeroId3;
}
