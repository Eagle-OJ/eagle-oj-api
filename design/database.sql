-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: eagle_oj
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` varchar(500) NOT NULL,
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `aid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `url` varchar(255) NOT NULL,
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '0 失效 待删除\n1 有效',
  `upload_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contest`
--

DROP TABLE IF EXISTS `contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '比赛名称',
  `owner` int(10) unsigned NOT NULL,
  `group` int(10) unsigned NOT NULL COMMENT '0 代表公告 大于0代表属于某一个小组',
  `slogan` varchar(100) NOT NULL COMMENT '标语',
  `description` varchar(500) NOT NULL,
  `start_time` bigint(13) unsigned NOT NULL,
  `end_time` bigint(13) unsigned NOT NULL COMMENT '0 代表永远不会结束',
  `total_time` bigint(13) unsigned DEFAULT NULL COMMENT '大于0则代表倒计时类型考试',
  `password` char(6) DEFAULT NULL,
  `official` tinyint(1) unsigned NOT NULL COMMENT '0非正式\n1官方',
  `type` tinyint(1) unsigned NOT NULL COMMENT '模式\n0普通模式\n1ACM模式\n时间\n0 普通模式 开始到结束时间\n1 限时模式\n0: 普通比赛 普通时间\n1:普通比赛 限时比赛\n2:ACM比赛 普通时间\n3:ACM比赛 限时比赛',
  `status` tinyint(1) unsigned NOT NULL COMMENT '0编辑中 1开启 2已结束',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contest_problem`
--

DROP TABLE IF EXISTS `contest_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_problem` (
  `cid` int(10) unsigned NOT NULL COMMENT '对应比赛id',
  `pid` int(10) unsigned NOT NULL,
  `display_id` int(10) unsigned NOT NULL,
  `score` int(10) unsigned NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  UNIQUE KEY `pid_cid_unique` (`pid`,`cid`),
  UNIQUE KEY `cid_displayId_unique` (`cid`,`display_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contest_problem_user`
--

DROP TABLE IF EXISTS `contest_problem_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_problem_user` (
  `cid` int(10) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `wrong_times` int(10) unsigned NOT NULL,
  `score` tinyint(3) unsigned NOT NULL,
  `status` enum('AC','WA','RTE','TLE','CE') NOT NULL,
  `solved_time` bigint(13) unsigned NOT NULL,
  `used_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `cid_pid_uid_unique` (`cid`,`pid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contest_user`
--

DROP TABLE IF EXISTS `contest_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_user` (
  `cid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `total_used_time` bigint(13) unsigned DEFAULT '0',
  `total_score` int(10) unsigned DEFAULT '0',
  `total_wrong_times` int(10) unsigned DEFAULT '0',
  `submit_times` int(10) unsigned DEFAULT '0',
  `finished_problems` int(10) unsigned DEFAULT '0',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  `join_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `cid_uid_union` (`cid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `gid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(6) DEFAULT NULL,
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_user`
--

DROP TABLE IF EXISTS `group_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_user` (
  `gid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `group_name` varchar(20) NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `finished_problems` int(10) unsigned DEFAULT '0',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  `join_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `gid_uid_unique` (`gid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `judger`
--

DROP TABLE IF EXISTS `judger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `judger` (
  `jid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `port` mediumint(6) unsigned NOT NULL,
  `add_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`jid`),
  UNIQUE KEY `unique_url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL COMMENT '0 代表global 全局消息',
  `type` tinyint(1) unsigned NOT NULL COMMENT '0 普通消息，显示content即可\n1 xxx 比赛结束的排行榜',
  `content` varchar(500) NOT NULL,
  `json_content` json NOT NULL,
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`mid`),
  KEY `owner_index` (`owner`),
  KEY `create_time_index` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `title` varchar(100) NOT NULL,
  `lang` json NOT NULL COMMENT 'json数组[PYTHON36,2,3,4]',
  `description` json NOT NULL,
  `input_format` json NOT NULL,
  `output_format` json NOT NULL,
  `difficult` tinyint(1) unsigned NOT NULL COMMENT '0:easy 1:middle 2:difficult 3:expert',
  `samples` json NOT NULL COMMENT 'json array\n[{input: 1, output:10}, {...}]',
  `time` tinyint(1) unsigned NOT NULL,
  `memory` smallint(5) unsigned NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `used_times` int(10) unsigned DEFAULT '0' COMMENT '被别人引用过的次数',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  `status` tinyint(1) unsigned NOT NULL COMMENT '0 自己使用\n1 审核中\n2 全局使用',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `difficult` (`difficult`),
  KEY `status` (`status`),
  KEY `owner` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem_moderator`
--

DROP TABLE IF EXISTS `problem_moderator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_moderator` (
  `pid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  UNIQUE KEY `UNIQUE` (`pid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem_user`
--

DROP TABLE IF EXISTS `problem_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_user` (
  `pid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `status` enum('AC','WA','RTE','TLE','CE') NOT NULL,
  UNIQUE KEY `unique` (`pid`,`uid`,`status`),
  KEY `index` (`pid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `key` varchar(20) NOT NULL,
  `value` varchar(1000) NOT NULL,
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `submission`
--

DROP TABLE IF EXISTS `submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission` (
  `sid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `cid` int(10) unsigned NOT NULL COMMENT '0 代表不属于非比赛提交',
  `gid` int(10) unsigned NOT NULL,
  `source_code` int(10) unsigned NOT NULL COMMENT '关联attachment id',
  `lang` enum('JAVA8','CPP','C','PYTHON27','PYTHON35') NOT NULL,
  `time` decimal(5,2) unsigned NOT NULL,
  `memory` smallint(5) unsigned NOT NULL COMMENT 'MB整数',
  `status` enum('AC','WA','RTE','TLE','CE') NOT NULL,
  `submit_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `OWNER_INDEX` (`owner`),
  KEY `PID_INDEX` (`pid`),
  KEY `CID_INDEX` (`cid`),
  KEY `STATUS_INDEX` (`status`),
  KEY `SUBMIT_TIME_INDEX` (`submit_time`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_problem`
--

DROP TABLE IF EXISTS `tag_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_problem` (
  `tid` int(10) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  UNIQUE KEY `UNIQUE` (`tid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `tid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `used` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`tid`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test_cases`
--

DROP TABLE IF EXISTS `test_cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_cases` (
  `tid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `stdin` varchar(1000) NOT NULL,
  `stdout` varchar(1000) NOT NULL,
  `strength` tinyint(1) unsigned NOT NULL COMMENT '介于1-9之间',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `avatar` int(10) unsigned DEFAULT '0',
  `password` varchar(32) NOT NULL,
  `role` tinyint(1) unsigned DEFAULT '0' COMMENT '0 普通用户\n9 管理员',
  `permission` json NOT NULL COMMENT '[''set'',''hello'']',
  `submit_times` int(10) unsigned DEFAULT '0',
  `contest_times` int(10) unsigned DEFAULT '0',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  `finished_problems` int(10) unsigned DEFAULT '0',
  `gender` tinyint(1) unsigned DEFAULT '0' COMMENT '0保密\n1男\n2女',
  `motto` varchar(50) DEFAULT NULL,
  `register_time` bigint(13) unsigned NOT NULL,
  `verified` tinyint(1) unsigned DEFAULT '0' COMMENT '0未验证\n1验证通过',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_log`
--

DROP TABLE IF EXISTS `user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_log` (
  `date` date NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `submit_times` int(10) unsigned NOT NULL,
  `ac_times` int(10) unsigned NOT NULL,
  `wa_times` int(10) unsigned NOT NULL,
  `rte_times` int(10) unsigned NOT NULL,
  `tle_times` int(10) unsigned NOT NULL,
  `ce_times` int(10) unsigned NOT NULL,
  UNIQUE KEY `unique` (`date`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-23 19:05:24
