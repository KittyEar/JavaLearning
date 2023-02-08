package com.gameserver.pojo.msg;

import lombok.Getter;

@Getter
public enum HttpStatus {
    SUCCESS(200, "成功"),
    ERROR(201, "失败"),

    TOKEN_ERROR(202, "TOKEN错误"),
    COIN_NOT_ENOUGH_ERROR(203, "元宝不足"),

    CLOSE_SERVER_PWD_ERROR(204, "关闭服务器密码错误"),
    SERVER_CLOSE(205, "服务器已经关闭"),

    REG_ACCOUNT_FORM_ERROR(1001, "账号格式错误,字母、数字、特殊符号组成，账号字数限制5-20个字符 特殊符号仅限 @ $ ^ ! ~ , . *"),
    REG_PASSWORD_FORM_ERROR(1002, "密码格式错误,字母、数字、特殊符号组成，账号字数限制8-16个字符 特殊符号仅限 @ $ ^ ! ~ , . *"),
    REG_DUPLICATE_ACCOUNT_ERROR(1003, "账号重名了,请换个账号名"),
    LOGIN_FIND_ACCOUNT_ERROR(1004, "账号不存在"),
    LOGIN_PASSWORD_ERROR(1005, "密码错误"),
    NOT_LOGIN_ERROR(1006, "请重新登录"),

    PLAYER_CREATE_ERROR(1101, "创建角色信息失败"),

    HERO_MOULD_ID_ERROR(1201, "英雄模板id错误"),
    HERO_CREATE_ERROR(1202, "创建英雄失败"),
    HERO_ID_ERROR(1203, "英雄id错误"),
    HERO_LV_MAX_ERROR(1204, "满级了"),
    HERO_STAR_MAX_ERROR(1205, "满星了"),
    HERO_STAR_UP_MATERIAL_ERROR(1206, "升星材料错误"),
    HERO_STAR_UP_DEL_ERROR(1207, "删除材料错误"),
    HERO_EXP_NOT_ENOUGH_ERROR(1208, "经验道具数量不足"),
    HERO_STAR_STONE_NOT_ENOUGH_ERROR(1208, "升星石头数量不足"),

    TAVERN_FORMAT_ERROR(1301, "日期错误"),
    TAVERN_FREE_LOTTERY_TIME_ERROR(1302, "免费抽奖时间还没有到"),
    TAVERN_ITEM_NOT_ENOUGH_ERROR(1303, "抽奖卷轴道具数量不足"),

    ITEM_NUM_ERROR(1401, "道具数量异常"),
    ITEM_ID_ERROR(1402, "道具id异常"),
    ITEM_NOT_ENOUGH_ERROR(1403, "道具数量不足"),
    ITEM_CANNOT_USE_ERROR(1404, "普通道具不能使用"),
    ITEM_CREATE_ERROR(1405, "创建道具失败"),

    UNKNOWN_ERROR(9999, "未知错误");

    private Integer code;
    private String msg;

    HttpStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}