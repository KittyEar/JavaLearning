package com.gameserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameserver.pojo.db.Hero;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HeroMapper extends BaseMapper<Hero> {
}
