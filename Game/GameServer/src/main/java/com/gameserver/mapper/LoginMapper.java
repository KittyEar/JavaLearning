package com.gameserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameserver.pojo.db.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository//持久层注解
public interface LoginMapper extends BaseMapper<Account> {
    //所有CRUD已经完成
    //Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能

}