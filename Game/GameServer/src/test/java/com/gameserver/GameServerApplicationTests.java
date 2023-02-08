package com.gameserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.mapper.HeroMapper;
import com.gameserver.pojo.db.Hero;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class GameServerApplicationTests {
    @Autowired
    private HeroMapper heroMapper;
    @Test
    void contextLoads() {
        List<Hero> list = heroMapper.selectList(new QueryWrapper<>());
        list.forEach(System.out::println);
    }
    public static void main(String[] args) {
        String[][] a = new String[][]{
                {"P",null,null,"I",null,null,"N"},
                {"P",null,"l","I",null,"p","N"},
                {"P","e",null,"I","n",null,null},
                {"P",null,null,"I",null,null,null}
        };
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j <a[0].length ; j++) {
                if(a[i][j] != null){
                    System.out.println(i + "   "+ j);
                }
            }
            //System.out.println(a[i][0] == null);
        }
       // System.out.println(4 & 7);
    }
    private static void solve3(String str, int n){

    }
    public static int compareVersion(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        int a = ver1.length;
        int b = ver2.length;
        int res = Arrays.stream(ver1).mapToInt(Integer::parseInt).sum() - Arrays.stream(ver2).mapToInt(Integer::parseInt).sum();
        return Integer.compare(res, 0);
    }
    private List<String> a(){
        return  new LinkedList<>();
    }
}