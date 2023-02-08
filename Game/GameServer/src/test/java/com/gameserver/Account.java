package com.gameserver;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private long id;     // 账户id
    private double blance;  // 账户余额

    // 省略构造方法和getter/setter

   /*


create table `my_table`(
        `id` long AUTO_INCREMENT,
        `name` varchar(16) NOT NULL,
        PRIMARY KEY (`id`),
        key `name` (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

select name from `my_table` where name like `a%`

select name from `my_table`  where name like `%z`

select name from `my_table`  where name like `a%` and name like `z%`

    */
}
