package com.gameserver.service;

import com.gameserver.dao.LoginDao;
import com.gameserver.pojo.db.Account;
import com.gameserver.pojo.msg.HttpStatus;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.server.login.SLoginMsg;
import com.gameserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {
    @Autowired
    private LoginDao loginDao;
    // 注册功能
    public SRet<?> reg(String account, String password){
        /*
         使用正则表达式判断账号格式是否正确
         字母、数字、特殊符号组成，账号字数限制5-20个字符 特殊符号仅限 @ $ ^ ! ~ , . *
         */
        if (!checkAccount(account)){
            return SRet.error(HttpStatus.REG_ACCOUNT_FORM_ERROR.getCode(), HttpStatus.REG_ACCOUNT_FORM_ERROR.getMsg());
        }

        /* 使用正则表达式判断密码格式是否正确
         字母、数字、特殊符号组成，账号字数限制8-16个字符 特殊符号仅限 @ $ ^ ! ~ , . *
         */
        if (!checkPassword(password)){
            return SRet.error(HttpStatus.REG_PASSWORD_FORM_ERROR.getCode(), HttpStatus.REG_PASSWORD_FORM_ERROR.getMsg());
        }
       /*  将客户端发来的密码进行MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        插入结果 = 使用mybatisplus插入一条数据到account表
        Account accountObj = new Account();
        accountObj.setAccount(account);
        accountObj.setPassword(md5Password);
        */
        try {
            loginDao.insert(account, password);//主键不会唯一会抛异常
        }catch (Exception e){
            return SRet.error(HttpStatus.REG_DUPLICATE_ACCOUNT_ERROR.getCode(), HttpStatus.REG_DUPLICATE_ACCOUNT_ERROR.getMsg());
        }
        return SRet.success();
    }

    public SRet<?> login(String account, String password){
        SLoginMsg sLoginMsg = new SLoginMsg();

//        查找后的Account对象 = 使用mybatisplus从account表查找账号为"参数账号"的一条数据
//        QueryWrapper<Account> objectQueryWrapper = new QueryWrapper<>();
//        objectQueryWrapper.eq("account", account);
        Account accountObj = loginDao.queryAccount(account);
        if (accountObj==null){
            // 没有这个账号
            return SRet.error(HttpStatus.LOGIN_FIND_ACCOUNT_ERROR.getCode(), HttpStatus.LOGIN_FIND_ACCOUNT_ERROR.getMsg());
        }
//
//        MD5加密后的密码 = MD5("参数密码");
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

//        if (MD5加密后的密码.equal(查找后的Account对象.get密码)){
//            // 需要创建JWT相关工具类（需要包含 根据id,account获取token，根据token获取id与account两个函数）
//            Token = JWT.getToken(查找后的Account对象.getId,账号);
//            设置sLoginMsg返回码200，并附加成功信息"登录成功";
//            sLoginMsg.SetToken(Token);
//            return sLoginMsg;
//        }
        if (md5Password.equals(accountObj.getPassword())){//密码是否相等
            // 相等 生成token 返回
            String token = JwtUtil.createAccToken(accountObj.getId());
            sLoginMsg.setToken(token);
            return SRet.success(sLoginMsg);
        }
        return SRet.error(HttpStatus.LOGIN_PASSWORD_ERROR.getCode(), HttpStatus.LOGIN_PASSWORD_ERROR.getMsg());

    }

    //校验创建事件页面账号格式
    private boolean checkAccount(String accountNumber) {
        String valicateAccount="^[\\w@\\$\\^!~,.\\*]{5,20}+$";
        Pattern pattern = Pattern.compile(valicateAccount);
        Matcher matcher = pattern.matcher(accountNumber);
        return matcher.matches();
    }

    //密码校验：
    private boolean checkPassword(String passWord) {
        String valicatePassword="^[\\w@\\$\\^!~,.\\*]{8,16}+$";
        Pattern pattern = Pattern.compile(valicatePassword);
        Matcher matcher = pattern.matcher(passWord);
        return matcher.matches();
    }
}
