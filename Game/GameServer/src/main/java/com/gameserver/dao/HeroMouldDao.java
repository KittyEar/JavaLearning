package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.HeroMouldMapper;
import com.gameserver.pojo.db.mould.HeroMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeroMouldDao {
    @Autowired
    private HeroMouldMapper heroMouldMapper;
    public List<HeroMould> getHeroMoulds(){
        QueryWrapper<HeroMould> wrapper = new QueryWrapper<>();
        return heroMouldMapper.selectList(wrapper);
    }
}
