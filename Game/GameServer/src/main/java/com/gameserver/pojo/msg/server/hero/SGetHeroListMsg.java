package com.gameserver.pojo.msg.server.hero;

import com.gameserver.pojo.db.Hero;
import lombok.Data;

import java.util.List;

@Data
public class SGetHeroListMsg {
    private List<Hero> heroList;
}
