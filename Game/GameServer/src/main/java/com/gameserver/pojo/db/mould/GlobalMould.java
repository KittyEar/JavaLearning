package com.gameserver.pojo.db.mould;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("config_global")
public class GlobalMould {
    private Integer id;
    private String name;
    private String value;
}
