package com.gameserver.scheduler;

import com.gameserver.cache.PlayerCache;
import com.gameserver.pojo.cache.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Scheduler {
    // 2分钟没有收到心跳就认为掉线了
    private final Integer LOGOUT_TIME = 2*60*1000;
    //private final Integer LOGOUT_TIME = 20*1000;

    @Autowired
    private PlayerCache playerCache;

    // 定时检测用户心跳 5分钟
    @Scheduled(fixedRate = 5*60*1000)
    //@Scheduled(fixedRate = 30*1000)
    public void heartBeatTask() {
//        long 当前时间 = System.currentTimeMillis();
//        for(遍历内存中所有playerInfo){
//            if(上次心跳的时间 + LOGOUT_TIME <当前时间){
//                saveOne(playerInfo);
//                从缓存中删除该用户;
//            }
//        }

        long curTime = System.currentTimeMillis();

        ConcurrentHashMap<Integer, PlayerInfo> playerInfos = playerCache.getPlayerInfos();
        for (PlayerInfo playerInfo : playerInfos.values()){
            if (playerInfo.getLastHeartBeatTime() + LOGOUT_TIME < curTime){
               // System.out.println("用户:"+playerInfo.getBaseProp().getId() + "下线了");
                //存到数据库
                playerCache.saveOne(playerInfo);
                //删除缓存中的内容
                playerInfos.remove(playerInfo.getBaseProp().getId());
            }
        }
    }
    //定时存储使用信息到数据库    一个小时
    @Scheduled(fixedRate = 60*60*1000)
    public void saveAll(){
        playerCache.saveAll();
    }
}
