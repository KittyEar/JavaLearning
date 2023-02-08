package com.gameserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameserver.pojo.db.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ItemMapper extends BaseMapper<Item> {

}