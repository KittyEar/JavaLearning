package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.ItemMouldMapper;
import com.gameserver.pojo.db.mould.ItemMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMouldDao {
    @Autowired
    private ItemMouldMapper itemMouldMapper;
    public List<ItemMould> getItemMoulds(){
        QueryWrapper<ItemMould> wrapper = new QueryWrapper<>();
        return itemMouldMapper.selectList(wrapper);
    }
}
