package com.gameserver.pojo.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("item")
@Accessors(chain = true)
public class Item extends BaseDBTable {
    private Integer playerId;
    private Integer typeId;
    private String name;
    private Integer num;
}

