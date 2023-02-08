package com.gameserver.pojo.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
// 注意1：指定实体类对象与数据库表的关联关系
@TableName("account")//剩下字段的内容自动添加
public class Account extends BaseDBTable {
    // 账号
    private String account;
    // 密码
    private String password;
}