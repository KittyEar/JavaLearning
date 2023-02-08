package com.gameserver.pojo.db.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("config_item")
public class ItemMould {
    private Integer id;
    private String name;
    private Integer type;
    private String prop;
}
