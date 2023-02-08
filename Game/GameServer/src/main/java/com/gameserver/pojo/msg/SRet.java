package com.gameserver.pojo.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
//将响应体泛型化
@Data
@AllArgsConstructor
public class SRet<T>{
    private Integer code;
    private String msg;
    private T data;

    public static <T> SRet<T> success(T data){
        return new SRet<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), data);
    }

    public static SRet<?> success(){
        return new SRet<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), null);
    }

    public static <T> SRet<T> error(T data){
        return new SRet<>(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), data);
    }

    public static SRet<?> error(){
        return new SRet<>(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), null);
    }

    public static SRet<?> error(Integer code, String msg){
        return new SRet<>(code, msg, null);
    }
}