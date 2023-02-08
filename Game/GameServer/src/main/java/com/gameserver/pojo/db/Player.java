package com.gameserver.pojo.db;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)//使用父类属性生成hash值
@Data
@TableName("player")
@Accessors(chain = true)
public class Player extends BaseDBTable{
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
    @ApiModelProperty("最后一次免费十连抽的时间")
    private String lastFreeLotteryTime;
}
