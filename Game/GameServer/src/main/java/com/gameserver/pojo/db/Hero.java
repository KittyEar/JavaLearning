package com.gameserver.pojo.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hero")
public class Hero extends BaseDBTable {
    private Integer playerId;
    private Integer typeId;
    private String heroName;
    private Integer att;
    private Integer def;
    private Integer maxHp;
    private Integer lv;
    private Integer star;
}
