package com.gameserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameserver.pojo.db.mould.ItemMould;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ItemMouldMapper extends BaseMapper<ItemMould> {

}
