package com.gameserver.service;

import com.gameserver.cache.PlayerCache;
import com.gameserver.config.heroConfig.GlobalConfig;
import com.gameserver.config.heroConfig.ItemConfig;
import com.gameserver.dao.ItemDao;
import com.gameserver.exceptions.gameException.item.*;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Item;
import com.gameserver.pojo.db.mould.ItemMould;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.server.item.SGetItemListMsg;
import com.gameserver.pojo.msg.server.item.SUseItemMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ItemService {
    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemConfig itemConfig;

    @Autowired
    private HeroService heroService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GlobalConfig globalConfig;

    // 从数据库中加载数据到内存
    public void initCache(PlayerInfo playerInfo){
        // 访问数据库，获取用户英雄信息
        Integer playerId = playerInfo.getBaseProp().getId();
        List<Item> items = itemDao.getItemsByPlayerId(playerId);

        // 将英雄数据放入缓存中
        playerInfo.setItems(items);
    }


    // 获取道具列表
    public SRet<SGetItemListMsg> getItemList(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        SGetItemListMsg sGetItemListMsg = new SGetItemListMsg();
        sGetItemListMsg.setItemList(items);
        return SRet.success(sGetItemListMsg);
    }

    // 使用道具

    /**
     *
     * @param playerId 玩家id
     * @param itemId  道具id
     * @param num   使用道具的数量
     * @return 成功或不成功的原因
     */
    public SRet<SUseItemMsg > useItem(Integer playerId, Integer itemId, Integer num){
        if(num<=0){
            throw new ItemNumErrorException();
        }

        Item item = getItem(playerId, itemId);
        ItemMould itemMould = itemConfig.getItemMould(item.getTypeId());
        switch (itemMould.getType()){//获取道具类型
            case ItemConfig.ITEM_TYPE_HERO_FRAGMENT: {
                // 英雄碎片
                Integer heroTypeId = Integer.valueOf(itemMould.getProp());
                //配置里规定一个英雄需要60个碎片
                Integer oneHeroFragmentNum = globalConfig.getIntValue(GlobalConfig.ONE_HERO_FRAGMENT_NUM);
                if (num%oneHeroFragmentNum!=0){//不是60的整数
                    throw new ItemNumErrorException();
                }
                int heroNum = num / oneHeroFragmentNum;//可以生成英雄的个数

                // 删除道具
                boolean b = delItem(item, num);
                if (b){
                    // 创建英雄
                    for (int i=0; i<heroNum; i++){
                        heroService.createHero(playerId, heroTypeId);
                    }
                }else{
                    // 道具数量不足
                    throw new ItemNotEnoughErrorException();
                }
                break;
            }
            case ItemConfig.ITEM_TYPE_HERO_COIN: {
                // 元宝， 看碎片面额，
                Integer coinNum = Integer.valueOf(itemMould.getProp());
                // 删除道具
                boolean b = delItem(item, num);
                if (b){
                    playerService.addCoin(playerId, coinNum*num);
                }else{
                    // 道具数量不足
                    throw new ItemNotEnoughErrorException();
                }
                break;
            }
            case ItemConfig.ITEM_TYPE_HERO_IDLE_PRICE: {
                // 挂机奖励
                // todo: 等关卡模块实现后来做
                break;
            }
            default:
                // 普通道具
                throw new ItemCanNotUseErrorException();
        }

        // 返回道具最新的数量
        SUseItemMsg sUseItemMsg = new SUseItemMsg();
        sUseItemMsg.setNewNum(item.getNum());
        return SRet.success(sUseItemMsg);
    }

    // 创建道具
    public void addItem(Integer playerId, Integer itemTypeId, Integer num){
        Item item = getItemByTypeId(playerId, itemTypeId);
        if (item==null){
            // 用户身上没有该typeId的道具
            Item newItem = addItemInDB(playerId, itemTypeId, num);

            // 再去内存中增加
            PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
            playerInfo.getItems().add(newItem);
        }else {
            // 用户身上有该typeId的道具
            Integer newNum = item.getNum() + num;
            item.setNum(newNum);
        }
    }

    // 在数据库中增加道具
    public Item addItemInDB(Integer playerId, Integer itemTypeId, Integer num){
        ItemMould itemMould = itemConfig.getItemMould(itemTypeId);
        if(itemMould == null){
            log.error("道具模板id不存在");
            throw new ItemCreateErrorException();
        }
        Item newItem = new Item();
        newItem.setPlayerId(playerId)
                .setTypeId(itemTypeId)
                .setNum(num)
                .setName(itemMould.getName());

        // 先去数据库里增加
        try {
            itemDao.insertItem(newItem);
        }catch (Exception e){
            log.error("createItem error: playerId=" + playerId+ " itemTypeId="+itemTypeId);
            throw new ItemCreateErrorException();
        }

        return newItem;
    }


    // 根据道具typeId删除道具
    public boolean delItemByTypeId(Integer playerId, Integer itemTypeId, Integer delNum){
        Item item = getItemByTypeId(playerId, itemTypeId);
        return delItem(item, delNum);
    }


    // 删除道具
    public boolean delItem(Integer playerId, Integer itemId, Integer delNum){
        Item item = getItem(playerId, itemId);
        return delItem(item, delNum);
    }

    // 删除道具
    public boolean delItem(Item item, Integer delNum){
//        if (item.num<num){
//            return false;
//        }else{
//            扣道具数量
//        }
//        return true;
        if (item.getNum()<delNum){
            // 道具数量不足
            return false;
        }else {
            int newNum = item.getNum() - delNum;
            item.setNum(newNum);
        }
        return true;
    }

    // 根据道具id获取道具信息
    private Item getItem(Integer playerId, Integer itemId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        for (Item item : items) {
            if (item.getId().equals(itemId)) {//获取对应itemId的item，后期应该考虑一下二分查找
                return item;
            }
        }
        throw new ItemIdErrorException();
    }

    // 根据道具typeId获取道具信息
    public Item getItemByTypeId(Integer playerId, Integer itemTypeId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Item> items = playerInfo.getItems();
        for (Item item : items) {
            if (item.getTypeId().equals(itemTypeId)) {
                return item;
            }
        }
        return null;
    }
}
