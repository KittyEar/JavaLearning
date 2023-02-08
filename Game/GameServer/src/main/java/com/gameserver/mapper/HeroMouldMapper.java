package com.gameserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameserver.pojo.db.mould.HeroMould;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HeroMouldMapper extends BaseMapper<HeroMould> {

}
