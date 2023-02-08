package com.gameserver.config.heroConfig;

import com.gameserver.dao.GlobalConfigDao;
import com.gameserver.pojo.db.mould.GlobalMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class GlobalConfig implements CommandLineRunner {

    @Autowired
    private GlobalConfigDao globalConfigDao;

    public static final String STAR_LV = "star_lv";
    public static final String MAX_STAR = "max_star";
    public static final String ONE_LOTTERY_COIN = "one_lottery_coin";
    public static final String TEN_LOTTERY_COIN = "ten_lottery_coin";
    public static final String FREE_LOTTERY_TIME = "free_lottery_time";
    public static final String ONE_HERO_FRAGMENT_NUM = "one_hero_fragment_num";
    public static final String EXP_ITEM_TYPE_ID = "exp_item_type_id";
    public static final String STAR_STONE_ITEM_TYPE_ID = "star_stone_item_type_id";
    public static final String LOTTERY_ITEM_TYPE_ID = "lottery_item_type_id";

    public static final String PLAYER_INIT_ITEMS = "player_init_items";
    public static final String PLAYER_INIT_HEAD = "player_init_head";
    public static final String PLAYER_INIT_LV = "player_init_lv";
    public static final String PLAYER_INIT_COIN = "player_init_coin";
    public static final String PLAYER_INIT_CHECKPOINT = "player_init_checkpoint";

    private HashMap<String, String> globalConfigMap = new HashMap<>();

    public String getStringValue(String key){
        return globalConfigMap.get(key);
    }

    public Integer getIntValue(String key){
        return Integer.valueOf(globalConfigMap.get(key));
    }

    @Override
    public void run(String... args) throws Exception {
        List<GlobalMould> globalMoulds = globalConfigDao.getGlobalMoulds();

        for (GlobalMould globalMould : globalMoulds) {
            String key = globalMould.getName();
            String value = globalMould.getValue();
            globalConfigMap.put(key, value);
        }
    }
}
