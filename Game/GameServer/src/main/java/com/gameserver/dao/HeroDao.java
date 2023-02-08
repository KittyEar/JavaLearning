package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.HeroMapper;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeroDao {
    @Autowired
    private HeroMapper heroMapper;
    public void updateById(Hero hero){
        heroMapper.updateById(hero);
    }
    public List<Hero> getHeroes(PlayerInfo playerInfo){
        QueryWrapper<Hero> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", playerInfo.getBaseProp().getId());
        return heroMapper.selectList(wrapper);
    }
    public void insertHero(Hero hero){
        heroMapper.insert(hero);
    }
    public void deleteHeroById(Integer heroId){
        heroMapper.deleteById(heroId);
    }
}
