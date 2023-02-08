package com.gameserver.config.heroConfig;

import com.gameserver.dao.HeroMouldDao;
import com.gameserver.pojo.db.mould.HeroMould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component                         //启动时会自动运行run方法
public class HeroConfig implements CommandLineRunner {

    @Autowired
    private HeroMouldDao heroMouldDao;

    private List<HeroMould> heroMouldList = new ArrayList<>();
    // 英雄总权重
    private Integer allWeight = 0;

    public Integer getAllWeight(){
        return allWeight;
    }
    // 每星增加等级上限
//    private Integer starLv = 20;
//    // 最大星级
//    private Integer maxStar = 5;

//    public Integer getStarLv(){
//        return starLv;
//    }
//
//    public Integer getMaxStar(){
//        return maxStar;
//    }

    public List<HeroMould> getHeroMouldList() {
        return heroMouldList;
    }

    public HeroMould getHeroMould(Integer typeId) {//模板id是否存在
        for (HeroMould heroMould : heroMouldList) {
            if (Objects.equals(heroMould.getId(), typeId)) {
                return heroMould;
            }
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        //从数据库加载模板

        List<HeroMould> heroMoulds = heroMouldDao.getHeroMoulds();
        heroMouldList.addAll(heroMoulds);

//        starLv = 20;
//        maxStar = 5;
        // 计算英雄总权重,做为缓存，免得修正十连抽时要重新算
        for (HeroMould heroMould : heroMouldList) {
            Integer weight = heroMould.getLotteryWeight();
            allWeight += weight;
        }
    }
}