package com.gameserver.pojo.msg.server.player;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel("玩家信息")
@Accessors(chain = true)
public class SGetInfo {
    private Integer accountId;
    @ApiModelProperty("玩家名称")
    private String nickName;
    @ApiModelProperty("玩家等级")
    private Integer lv;
    @ApiModelProperty("头像")
    private Integer profile;
    @ApiModelProperty("元宝/点券")
    private Integer coin;
    @ApiModelProperty("关卡点")
    private Integer checkPoint;
    @ApiModelProperty("新token")
    private String newToken;
}
