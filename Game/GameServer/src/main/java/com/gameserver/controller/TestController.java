package com.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @CrossOrigin
    @GetMapping("/tests")
    public String test(){
       return JSONObject.toJSON(new Res()).toString();
    }
}
@Data
class Res {
    private final String name = "KittyGuy";
    private final String url = "www.baidu.com";
}
