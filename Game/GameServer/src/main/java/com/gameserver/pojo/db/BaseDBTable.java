package com.gameserver.pojo.db;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
public class BaseDBTable {
    @TableId(type= IdType.AUTO)
    protected Integer id;
    // 逻辑删除
    @TableLogic
    protected Integer del;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    protected String createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateTime;
}
