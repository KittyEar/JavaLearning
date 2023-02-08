package com.gameserver.pojo.msg.server.tavern;

import lombok.Data;

import java.util.List;

@Data
public class STenItemLotteryMsg {
    private List<Integer> heroTypeIdList;
    private Integer newItemNum;
}
