package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.LoginMapper;
import com.gameserver.pojo.db.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class LoginDao {
    @Autowired
    private LoginMapper loginMapper;

    public Account getById(int a){
        return loginMapper.selectById(a);
    }
    public int insert(String account, String password) throws Exception{
        //        将客户端发来的密码进行MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
      //      插入结果 = 使用mybatisplus插入一条数据到account表
        Account accountObj = new Account();
        accountObj.setAccount(account);
        accountObj.setPassword(md5Password);
        return loginMapper.insert(accountObj);
    }
    public Account queryAccount(String account){
        //        查找后的Account对象 = 使用mybatisplus从account表查找账号为"参数账号"的一条数据
        QueryWrapper<Account> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("account", account);
        return loginMapper.selectOne(objectQueryWrapper);
    }

}
