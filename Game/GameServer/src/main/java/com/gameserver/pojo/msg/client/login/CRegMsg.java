package com.gameserver.pojo.msg.client.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("注册消息结构")
public class CRegMsg {
    @ApiModelProperty("注册请求的账户名")
    private String account;
    @ApiModelProperty("注册请求的密码")
    private String password;
}
