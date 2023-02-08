package com.gameserver.pojo.msg.server.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录消息返回结构")
public class SLoginMsg {
    private String token;
}
