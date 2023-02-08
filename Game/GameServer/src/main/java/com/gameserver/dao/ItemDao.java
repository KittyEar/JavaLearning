package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.ItemMapper;
import com.gameserver.pojo.db.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDao {
    @Autowired
    private ItemMapper itemMapper;
    public List<Item> getItemsByPlayerId(Integer playerId){
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("player_id", playerId);
        return itemMapper.selectList(wrapper);
    }
    public void insertItem(Item item){
        itemMapper.insert(item);
    }
    public void updateItemById(Item item){
        itemMapper.updateById(item);
    }
}
