package com.gameserver.config.heroConfig;

import com.gameserver.dao.ItemMouldDao;
import com.gameserver.pojo.db.mould.ItemMould;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//道具配置
@Component
@Slf4j
public class ItemConfig implements CommandLineRunner {

    // 普通
    public final static int ITEM_TYPE_HERO_NOR = 1;
    // 英雄碎片
    public final static int ITEM_TYPE_HERO_FRAGMENT = 2;
    // 元宝
    public final static int ITEM_TYPE_HERO_COIN = 3;
    // 挂机奖励
    public final static int ITEM_TYPE_HERO_IDLE_PRICE = 4;



    private List<ItemMould> itemMouldList = new ArrayList<>();

    @Autowired
    private ItemMouldDao itemMouldDao;

    // 通过道具模板id获取模板信息
    public ItemMould getItemMould(Integer typeId){
        for (int i=0; i<itemMouldList.size(); i++){
            ItemMould itemMould = itemMouldList.get(i);
            if (itemMould.getId().equals(typeId)){
                return itemMould;
            }
        }
        log.debug("未找到具模板id为"+typeId+"的道具");
        return null;
    }


    @Override
    public void run(String... args) throws Exception {
        // 加载配置
       //  根据需求增加配置到itemMouldList
        List<ItemMould> itemMoulds = itemMouldDao.getItemMoulds();
        this.itemMouldList.addAll(itemMoulds);
    }
}
