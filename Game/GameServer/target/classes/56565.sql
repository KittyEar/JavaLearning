/*
SQLyog Enterprise v13.1.1 (64 bit)
MySQL - 8.0.22 : Database - game_server
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`game_server` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `game_server`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(255) NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码（MD5加密）',
  `del` int DEFAULT '0' COMMENT '逻辑删除',
  `create_time` varchar(19) DEFAULT '' COMMENT '创建时间',
  `update_time` varchar(19) DEFAULT '' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) COMMENT '账号索引'
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `account` */

insert  into `account`(`id`,`account`,`password`,`del`,`create_time`,`update_time`) values 
(16,'kittyguy','25f9e794323b453885f5181f1b624d0b',0,'2022-06-19 14:09:03','2022-06-19 14:09:03'),
(5,'test001','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-20 21:44:07','2021-10-20 21:44:07'),
(6,'test002','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-20 21:47:44','2021-10-20 21:47:44'),
(7,'test003','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-20 22:38:04','2021-10-20 22:38:04'),
(8,'test004','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-21 19:42:13','2021-10-21 19:42:13'),
(9,'test005','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-21 19:46:44','2021-10-21 19:46:44'),
(10,'test006','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-21 19:51:13','2021-10-21 19:51:13'),
(11,'test007','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-21 19:55:16','2021-10-21 19:55:16'),
(13,'test008','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-10-25 20:18:31','2021-10-25 20:18:31'),
(14,'test009','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-11-12 22:41:08','2021-11-12 22:41:08'),
(15,'test010','787ca7e12a2fa991cea5051a64b49d0c',0,'2021-11-12 22:49:00','2021-11-12 22:49:00');

/*Table structure for table `config_global` */

DROP TABLE IF EXISTS `config_global`;

CREATE TABLE `config_global` (
  `id` int NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置的名字',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '属性的约定值',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全局属性配置';

/*Data for the table `config_global` */

insert  into `config_global`(`id`,`name`,`value`,`des`) values 
(1,'star_lv','20','每星可提升的最大等级'),
(2,'max_star','5','英雄最大星级'),
(3,'one_lottery_coin','250','单抽元宝消耗数量'),
(4,'ten_lottery_coin','2200','10连抽元宝消耗数量'),
(5,'free_lottery_time','86400','免费抽奖间隔时间 单位：秒'),
(6,'one_hero_fragment_num','60','一个英雄所需碎片数量'),
(7,'player_init_items','200001,99,300001,99,400001,99,100001,99,100002,99,100003,99','新用户初始道具列表'),
(8,'exp_item_type_id','100002','英雄经验道具模板id'),
(9,'star_stone_item_type_id','100001','英雄升星石头道具模板id'),
(10,'lottery_item_type_id','100003','抽奖卷轴道具模板id'),
(11,'player_init_head','1','初始头像id'),
(12,'player_init_lv','1','初始角色等级'),
(13,'player_init_coin','999','初始元宝数量'),
(14,'player_init_checkpoint','1','初始关卡');

/*Table structure for table `config_hero` */

DROP TABLE IF EXISTS `config_hero`;

CREATE TABLE `config_hero` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '模板id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板名称',
  `init_att` int DEFAULT NULL COMMENT '初始攻击力',
  `init_def` int DEFAULT NULL COMMENT '初始防御力',
  `init_max_hp` int DEFAULT NULL COMMENT '初始血量',
  `lv_up_att` int DEFAULT NULL COMMENT '每一级提供的攻击力',
  `lv_up_def` int DEFAULT NULL COMMENT '每一级提供的防御力',
  `lv_up_max_hp` int DEFAULT NULL COMMENT '每一级提供的血量',
  `lottery_weight` int DEFAULT NULL COMMENT '几连抽会中',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='英雄模板';

/*Data for the table `config_hero` */

insert  into `config_hero`(`id`,`name`,`init_att`,`init_def`,`init_max_hp`,`lv_up_att`,`lv_up_def`,`lv_up_max_hp`,`lottery_weight`) values 
(1,'齐天大圣孙悟空',100,20,1000,10,2,100,100),
(2,'野猪精',80,16,1000,8,1,120,1000),
(3,'蜘蛛精',80,11,1000,11,1,100,1000);

/*Table structure for table `config_item` */

DROP TABLE IF EXISTS `config_item`;

CREATE TABLE `config_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL COMMENT '英雄碎片/元宝/挂机x小时奖励/普通道具',
  `prop` varchar(20) DEFAULT NULL COMMENT '额外属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=400002 DEFAULT CHARSET=utf8;

/*Data for the table `config_item` */

insert  into `config_item`(`id`,`name`,`type`,`prop`) values 
(100001,'升阶石头',1,'1000'),
(100002,'英雄经验',1,NULL),
(100003,'抽奖卷轴',1,NULL),
(200001,'齐天大圣孙悟空碎片',2,'1'),
(300001,'100元宝',3,'100'),
(400001,'挂机1小时奖励',4,'1000');

/*Table structure for table `hero` */

DROP TABLE IF EXISTS `hero`;

CREATE TABLE `hero` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '英雄id',
  `player_id` int DEFAULT NULL COMMENT '玩家id',
  `type_id` int DEFAULT NULL COMMENT '英雄模板id',
  `hero_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '英雄名',
  `att` int DEFAULT NULL COMMENT '攻击力，上限为星级*10',
  `def` int DEFAULT NULL COMMENT '防御力',
  `max_hp` int DEFAULT NULL COMMENT '血量',
  `lv` int DEFAULT NULL COMMENT '等级',
  `star` int DEFAULT NULL COMMENT '星级',
  `del` int DEFAULT '0' COMMENT '逻辑删除',
  `create_time` varchar(19) DEFAULT '' COMMENT '创建时间',
  `update_time` varchar(19) DEFAULT '' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

/*Data for the table `hero` */

insert  into `hero`(`id`,`player_id`,`type_id`,`hero_name`,`att`,`def`,`max_hp`,`lv`,`star`,`del`,`create_time`,`update_time`) values 
(54,3,1,'齐天大圣孙悟空',100,20,1000,1,1,0,'2021-10-23 19:00:54','2021-10-23 19:00:54'),
(55,1,2,'野猪精',160,26,2200,11,1,0,'2021-10-23 19:28:39','2022-06-19 14:56:58'),
(56,1,3,'蜘蛛精',80,11,1000,1,1,1,'2021-10-23 19:28:45','2021-11-12 22:02:10'),
(57,1,2,'野猪精',80,16,1000,1,1,1,'2021-10-24 21:27:27','2021-11-12 22:02:10'),
(58,1,2,'野猪精',80,16,1000,1,1,1,'2021-10-24 21:27:30','2021-10-24 21:27:30'),
(59,1,2,'野猪精',80,16,1000,1,1,1,'2021-10-24 21:27:39','2021-10-24 21:27:39'),
(60,1,1,'齐天大圣孙悟空',100,20,1000,1,1,1,'2021-10-24 21:28:26','2021-10-24 21:28:26'),
(61,1,1,'齐天大圣孙悟空',100,20,1000,1,2,0,'2021-10-24 21:28:26','2022-06-19 14:56:58'),
(62,1,1,'齐天大圣孙悟空',100,20,1000,1,1,1,'2021-10-24 21:28:26','2021-11-12 22:02:10'),
(63,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 20:20:00','2021-10-25 21:23:17'),
(64,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(65,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(66,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(67,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(68,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(69,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(70,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(71,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(72,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(73,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:52','2021-10-25 21:23:17'),
(74,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(75,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(76,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(77,9,1,'齐天大圣孙悟空',100,20,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(78,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(79,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(80,9,1,'齐天大圣孙悟空',100,20,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(81,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(82,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(83,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(84,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(85,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(86,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(87,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:55','2021-10-25 21:23:17'),
(88,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(89,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(90,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(91,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(92,9,2,'野猪精',80,16,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(93,9,3,'蜘蛛精',80,11,1000,1,1,0,'2021-10-25 21:17:56','2021-10-25 21:23:17'),
(94,1,1,'齐天大圣孙悟空',100,20,1000,1,1,0,'2021-11-11 10:59:33','2022-06-19 14:56:58'),
(95,1,3,'蜘蛛精',80,11,1000,1,1,0,'2021-11-12 22:14:15','2022-06-19 14:56:58'),
(96,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(97,1,3,'蜘蛛精',80,11,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(98,1,3,'蜘蛛精',80,11,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(99,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(100,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(101,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(102,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(103,1,3,'蜘蛛精',80,11,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(104,1,2,'野猪精',80,16,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58'),
(105,1,3,'蜘蛛精',80,11,1000,1,1,0,'2021-11-12 22:14:35','2022-06-19 14:56:58');

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `del` int DEFAULT '0',
  `create_time` varchar(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `player_id` int DEFAULT NULL,
  `type_id` int DEFAULT NULL COMMENT '背包物品模板id，来自config',
  `num` int DEFAULT NULL COMMENT '数量',
  `name` varchar(255) DEFAULT NULL COMMENT '物品名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `item` */

insert  into `item`(`id`,`del`,`create_time`,`update_time`,`player_id`,`type_id`,`num`,`name`) values 
(1,NULL,'2021-11-10 22:15:35','2021-11-10 22:15:35',1,100001,20,'升阶石头'),
(5,0,'2021-11-12 21:38:37','2022-06-19 14:56:58',1,200001,100,'齐天大圣孙悟空碎片'),
(6,0,'2021-11-12 21:58:50','2022-06-19 14:56:58',1,100001,100,'升阶石头'),
(7,0,'2021-11-12 21:59:06','2022-06-19 14:56:58',1,100002,100,'英雄经验'),
(8,0,'2021-11-12 21:59:28','2022-06-19 14:56:58',1,100003,133,'抽奖卷轴'),
(9,0,'2021-11-12 21:59:40','2022-06-19 14:56:58',1,300001,100,'100元宝'),
(10,0,'2021-11-12 21:59:46','2022-06-19 14:56:58',1,400001,100,'挂机1小时奖励'),
(11,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,200001,99,'齐天大圣孙悟空碎片'),
(12,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,300001,99,'100元宝'),
(13,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,400001,99,'挂机1小时奖励'),
(14,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,100001,99,'升阶石头'),
(15,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,100002,99,'英雄经验'),
(16,0,'2021-11-12 22:49:14','2021-11-12 22:49:14',11,100003,99,'抽奖卷轴');

/*Table structure for table `player` */

DROP TABLE IF EXISTS `player`;

CREATE TABLE `player` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '玩家id',
  `account_id` int NOT NULL COMMENT '外键，account表id',
  `nick_name` varchar(16) DEFAULT NULL COMMENT '玩家名称',
  `lv` int DEFAULT '0' COMMENT '玩家等级',
  `profile` int DEFAULT '0' COMMENT '头像',
  `coin` int DEFAULT '0' COMMENT '元宝',
  `check_point` int DEFAULT '0' COMMENT '关卡',
  `del` int DEFAULT '0' COMMENT '逻辑删除',
  `create_time` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(30) DEFAULT NULL COMMENT '更新时间',
  `last_free_lottery_time` varchar(20) DEFAULT NULL COMMENT '最后一次免费十连抽的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_pk` (`account_id`),
  KEY `player_id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `player` */

insert  into `player`(`id`,`account_id`,`nick_name`,`lv`,`profile`,`coin`,`check_point`,`del`,`create_time`,`update_time`,`last_free_lottery_time`) values 
(1,5,'player001',1,0,999,1,0,'2021-10-21 16:44:40','2022-06-19 14:56:58',NULL),
(2,6,'player001',1,0,999,1,0,'2021-10-21 17:36:11','2021-10-21 17:36:11',NULL),
(3,7,'player001',1,0,999,1,0,'2021-10-21 19:34:41','2021-10-21 19:34:41',NULL),
(4,8,'player001',1,0,999,1,0,'2021-10-21 19:42:36','2021-10-21 19:42:36',NULL),
(5,9,'player001',1,0,999,1,0,'2021-10-21 19:47:09','2021-10-21 19:47:09',NULL),
(6,10,'player001',1,0,999,1,0,'2021-10-21 19:51:58','2021-10-21 19:51:58',NULL),
(7,11,'player001',1,0,999,1,0,'2021-10-21 19:56:15','2021-10-21 19:56:15',NULL),
(9,13,'player001',1,0,992400,1,0,'2021-10-25 20:19:02','2021-10-25 21:23:17','2021-10-25 20:20:00'),
(10,14,'player001',1,0,999,1,0,'2021-11-12 22:41:42','2021-11-12 22:47:10','1970-01-01 08:00:00'),
(11,15,'player001',1,1,999,1,0,'2021-11-12 22:49:14','2021-11-12 22:53:27','1970-01-01 08:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
