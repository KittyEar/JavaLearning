package com.gameserver.controller;

import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.client.item.CAddItemMsg;
import com.gameserver.pojo.msg.client.item.CUseItemMsg;
import com.gameserver.service.ItemService;
import com.gameserver.utils.ServletUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/addItem")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> addItem(HttpServletRequest request, @RequestBody CAddItemMsg cAddItemMsg){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        itemService.addItem(playerId, cAddItemMsg.getItemTypeId(), cAddItemMsg.getNum());
        return SRet.success();
    }

    // 获取所有道具信息
    @PostMapping("/getItemList")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> getItemList(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
//        通过itemService.getItemList获取列表
        return itemService.getItemList(playerId);
    }

    // 使用道具
    @PostMapping("/useItem")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> useItem(HttpServletRequest request, @RequestBody CUseItemMsg cUseItemMsg){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return itemService.useItem(playerId, cUseItemMsg.getItemId(), cUseItemMsg.getNum());
    }
}
