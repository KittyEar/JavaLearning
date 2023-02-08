package com.gameserver.service;

import org.springframework.stereotype.Service;

@Service
public class GlobalService {
    // 服务器是否是关闭状态
    public boolean isClose = false;
}