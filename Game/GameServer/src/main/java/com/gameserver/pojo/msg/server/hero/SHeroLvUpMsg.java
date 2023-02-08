package com.gameserver.pojo.msg.server.hero;


import com.gameserver.pojo.db.Hero;
import lombok.Data;

@Data
public class SHeroLvUpMsg {
    private Hero hero;
}
