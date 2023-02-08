package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.ConfigGlobalMapper;
import com.gameserver.pojo.db.mould.GlobalMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalConfigDao {
    @Autowired
    private ConfigGlobalMapper configGlobalMapper;
    public List<GlobalMould> getGlobalMoulds(){
        QueryWrapper<GlobalMould> wrapperGlobal = new QueryWrapper<>();
        return configGlobalMapper.selectList(wrapperGlobal);
    }
}
