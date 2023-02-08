package com.gameserver.pojo.msg.server.tavern;

import lombok.Data;

import java.util.List;

@Data
public class STenCoinLotteryMsg {
    private List<Integer> heroTypeIdList;
    private Integer newCoin;
}
