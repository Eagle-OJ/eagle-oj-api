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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (1,14,'123123dse',1,1510486741972),(2,14,'123123dse',1,1510486744070),(3,14,'2017/10/13/d6cb7a12-a4f6-445b-bf02-3f8511de784f.py',1,1510564350031),(4,14,'2017/10/13/cd699257-290c-4171-ba54-dbad883ab204.py',1,1510571431147),(5,14,'2017/10/13/e7a2c2f6-ca0b-4406-8daf-1b4b33da4514.py',1,1510571480147),(6,14,'2017/10/13/c9350af9-ca10-42fd-a090-c225756d79a8.py',1,1510571633307),(7,14,'2017/10/13/5abf6874-3282-4798-aad5-63d3a7071d89.py',1,1510571829273),(8,14,'2017/10/14/791c98cb-4a81-49c7-a07d-e5283ee1c19e.py',1,1510647016892),(9,14,'2017/10/14/25a4c4f8-e520-4284-ab58-8f8b4b62a5d9.py',1,1510647023431),(10,14,'2017/10/16/c55be3cf-a643-4f1d-be05-e04413d004c7.py',1,1510832409352),(11,14,'2017/10/16/bf3699b3-2f0b-4b4a-97b4-8cb1d6cb5c66.py',1,1510832619465),(12,14,'2017/10/16/fcdf88b6-b9ac-4e6f-9fd7-da9b5d9677a6.py',1,1510832751645),(13,14,'2017/10/16/503c9554-af52-41ef-ac17-ec6c267da7ee.py',1,1510833023748),(14,14,'2017/10/16/c704a75e-1ee3-4c22-852b-c41ca0c2d838.py',1,1510833257525),(15,14,'2017/10/16/6ef2c421-56e9-48f2-b059-c2a2e2e7cd1e.py',1,1510833536594),(16,14,'2017/10/16/d6284e97-1906-4f3a-97f0-f2e82e7f8750.py',1,1510833624006),(17,14,'2017/10/16/de5d284a-5fe5-4735-ae9e-2d47264548d8.py',1,1510833767319),(18,14,'2017/10/16/105e0bee-8377-4e2c-be17-d4252c11688a.py',1,1510833814878),(19,14,'2017/10/16/4a4b7a54-0586-4d18-8185-bcb198942adb.py',1,1510833897877),(20,14,'2017/10/16/9cf73a09-a74d-40b0-a7a5-e9938f602e80.py',1,1510833928528),(21,14,'2017/10/16/014b5e6e-88c8-4094-96b5-914991c601b9.py',1,1510833962117),(22,14,'2017/10/16/4c076b51-db6c-46c2-8e99-0737e3373651.py',1,1510835715417),(23,14,'2017/10/16/b51a79a5-cf1c-46d5-8ffa-415034674edb.py',1,1510835767436),(24,14,'2017/10/16/683f3f49-881e-42c5-8e37-fd8a3b6ff3e9.py',1,1510836121338),(25,14,'2017/10/16/ecd46388-e905-465f-88c5-971ee95f6aed.py',1,1510836256961);
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

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
  `slogan` varchar(100) NOT NULL COMMENT '标语',
  `description` varchar(500) NOT NULL,
  `start_time` bigint(13) unsigned NOT NULL,
  `end_time` bigint(13) unsigned NOT NULL COMMENT '0 代表永远不会结束',
  `total_time` bigint(13) unsigned NOT NULL COMMENT '大于0则代表倒计时类型考试',
  `password` varchar(6) DEFAULT NULL,
  `official` tinyint(1) unsigned NOT NULL COMMENT '0非正式\n1官方',
  `type` tinyint(1) unsigned NOT NULL COMMENT '模式\n0普通模式\n1ACM模式\n时间\n0 普通模式 开始到结束时间\n1 限时模式\n0: 0+0\n1:0+1\n2:1+0\n3:1+1',
  `status` tinyint(1) unsigned NOT NULL COMMENT '0关闭 1开启',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest`
--

LOCK TABLES `contest` WRITE;
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
INSERT INTO `contest` VALUES (3,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,150000000000100,5,NULL,0,1,0,1510403394474),(4,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,150000000000100,5,'1',0,1,0,1510403786772),(5,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,150000000000100,5,'1',0,1,0,1510464222315),(6,'这是一个牛逼的比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,150000000000100,5,'1',0,0,1,1510832002472);
/*!40000 ALTER TABLE `contest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest_problem_user_info`
--

DROP TABLE IF EXISTS `contest_problem_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_problem_user_info` (
  `cid` int(10) unsigned NOT NULL,
  `pid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `score` tinyint(2) unsigned NOT NULL,
  `status` enum('Accepted','WrongAnswer','TimeLimitExceeded','CompileError','RuntimeError','JudgeError') NOT NULL,
  UNIQUE KEY `cid_pid_uid_unique` (`cid`,`pid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_problem_user_info`
--

LOCK TABLES `contest_problem_user_info` WRITE;
/*!40000 ALTER TABLE `contest_problem_user_info` DISABLE KEYS */;
INSERT INTO `contest_problem_user_info` VALUES (6,54,14,7,'Accepted');
/*!40000 ALTER TABLE `contest_problem_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest_user_info`
--

DROP TABLE IF EXISTS `contest_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_user_info` (
  `cid` int(11) unsigned NOT NULL,
  `uid` int(11) unsigned NOT NULL,
  `submit_times` int(11) unsigned DEFAULT '0',
  `accept_times` int(11) unsigned DEFAULT '0',
  `newly_accept_time` bigint(13) unsigned DEFAULT '0',
  `join_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `cid_uid_union` (`cid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_user_info`
--

LOCK TABLES `contest_user_info` WRITE;
/*!40000 ALTER TABLE `contest_user_info` DISABLE KEYS */;
INSERT INTO `contest_user_info` VALUES (1,12,0,0,0,1510559765984),(3,14,0,0,0,1510403705466),(4,14,0,0,0,1510403804620),(6,14,9,1,0,1510832042252);
/*!40000 ALTER TABLE `contest_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL,
  `cover` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(6) DEFAULT NULL,
  `create_time` bigint(13) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,14,0,'我们是胜利之队','123sda',1510486465720);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_user_info`
--

DROP TABLE IF EXISTS `group_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_user_info` (
  `gid` int(11) unsigned NOT NULL,
  `uid` int(11) unsigned NOT NULL,
  `score` int(10) unsigned DEFAULT '0',
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  `join_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `gid_uid_unique` (`gid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_user_info`
--

LOCK TABLES `group_user_info` WRITE;
/*!40000 ALTER TABLE `group_user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_user_info` ENABLE KEYS */;
UNLOCK TABLES;

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
  `code_language` json NOT NULL COMMENT 'json数组[1,2,3,4]',
  `description` varchar(1000) NOT NULL,
  `difficult` tinyint(1) unsigned NOT NULL COMMENT '0:easy 1:middle 2:difficult 3:expert',
  `input_format` varchar(200) NOT NULL,
  `output_format` varchar(200) NOT NULL,
  `constraint` varchar(200) NOT NULL,
  `sample` json NOT NULL COMMENT 'json array\n[{input: 1, output:10}, {...}]',
  `moderator` json NOT NULL COMMENT 'json array\n[1,2,3,54]',
  `tag` json NOT NULL COMMENT 'json array\n[1,2,3,4,5,6]',
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  `share` tinyint(1) unsigned NOT NULL COMMENT '0 自己使用\n1 审核中\n2 全局使用',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (1,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510133001856),(3,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510137594057),(4,1,'A+B Test Problem','[1, 2]','这是一道很简单的测试题，做A+B',1,'输入规范','输出规范','条件约束','[{\"input\": \"1,2,3\", \"output\": \"3,4,6\"}, {\"input\": \"1,2,3\", \"output\": \"3,4,6\"}]','[1, 2]','[\"链表\", \"data structure\"]',0,0,1,1510155806317),(5,1,'A+B Test Problem','[1, 2]','这是一道很简单的测试题，做A+B',1,'输入规范','输出规范','条件约束','[{\"input\": \"1,2,3\", \"output\": \"3,4,6\"}, {\"input\": \"1,2,3\", \"output\": \"3,4,6\"}]','[1, 2]','[\"链表\", \"data structure\"]',0,0,1,1510155937092),(6,1,'A+B Test Problem','[1, 2]','这是一道很简单的测试题，做A+B',1,'输入规范','输出规范','条件约束','[{\"input\": \"1,2,3\", \"output\": \"3,4,6\"}, {\"input\": \"1,2,3\", \"output\": \"3,4,6\"}]','[1, 2]','[\"链表\", \"data structure\"]',0,0,1,1510155974292),(7,1,'A+B Test Problem','[1, 2]','这是一道很简单的测试题，做A+B',1,'输入规范','输出规范','条件约束','[{\"input\": \"1,2,3\", \"output\": \"3,4,6\"}, {\"input\": \"1,2,3\", \"output\": \"3,4,6\"}]','[1, 2]','[\"链表\", \"data structure\"]',0,0,1,1510155974906),(8,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510203568179),(9,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510207430914),(10,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510207555221),(11,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510207578307),(12,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510208130947),(13,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510379347391),(14,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510379353632),(15,14,'A+B problem updated','[1, 3, 4]','这是一道很简单的题目让你们练练手',0,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[1, 2, 3]','[1, 3, 4, 5]',0,0,2,1510464328957),(16,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510462910373),(19,14,'A+B problemxx','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510463003126),(21,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464252616),(22,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464328801),(23,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464352016),(24,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464352748),(25,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464353151),(26,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464353514),(27,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464353833),(28,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464354119),(29,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464354529),(30,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464354753),(31,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464355256),(32,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464355572),(33,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464355997),(34,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464356215),(35,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464356483),(36,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464356664),(37,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464356957),(38,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464357246),(39,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464357562),(40,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464357881),(41,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464358221),(42,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464358577),(43,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464358812),(44,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464359094),(45,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464359277),(46,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464359531),(47,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464359751),(48,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464365334),(49,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464365742),(50,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464365982),(51,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464366273),(52,14,'A+B problem','[1, 3, 4]','这是一道很简单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',0,0,1,1510464384639),(53,1,'A+B Test Problem','[1, 2]','这是一道很简单的测试题，做A+B',1,'输入规范','输出规范','条件约束','[{\"input\": \"1,2,3\", \"output\": \"3,4,6\"}, {\"input\": \"1,2,3\", \"output\": \"3,4,6\"}]','[1, 2]','[\"链表\", \"data structure\"]',0,0,1,1510465276410),(54,14,'A+B problem','[1, 3, 4]','这是一道很简xx单的题目让你们练练手',1,'不用输入','','','[{\"input\": \"123\", \"output\": \"xxx\"}, {\"input\": \"11\", \"output\": \"xxx123123\"}]','[]','[1, 3, 4, 5]',14,2,1,1510832220276);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_contest_info`
--

DROP TABLE IF EXISTS `problem_contest_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_contest_info` (
  `pid` int(10) unsigned NOT NULL,
  `cid` int(10) unsigned NOT NULL COMMENT '对应比赛id',
  `score` int(10) unsigned NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  UNIQUE KEY `pid_cid_unique` (`pid`,`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_contest_info`
--

LOCK TABLES `problem_contest_info` WRITE;
/*!40000 ALTER TABLE `problem_contest_info` DISABLE KEYS */;
INSERT INTO `problem_contest_info` VALUES (3,0,0,0,0),(4,0,0,0,0),(5,0,0,0,0),(6,0,0,0,0),(7,0,0,0,0),(8,0,0,0,0),(9,0,0,0,0),(10,0,0,0,0),(11,0,0,0,0),(12,0,0,0,0),(13,0,0,0,0),(14,0,0,0,0),(15,0,0,2,2),(15,4,5,0,0),(21,0,0,0,0),(22,0,0,0,0),(23,0,0,0,0),(24,0,0,0,0),(25,0,0,0,0),(26,0,0,0,0),(27,0,0,0,0),(28,0,0,0,0),(29,0,0,0,0),(30,0,0,0,0),(31,0,0,0,0),(32,0,0,0,0),(33,0,0,0,0),(34,0,0,0,0),(35,0,0,0,0),(36,0,0,0,0),(37,0,0,0,0),(38,0,0,0,0),(39,0,0,0,0),(40,0,0,0,0),(41,0,0,0,0),(42,0,0,0,0),(43,0,0,0,0),(44,0,0,0,0),(45,0,0,0,0),(46,0,0,0,0),(47,0,0,0,0),(48,0,0,0,0),(49,0,0,0,0),(50,0,0,0,0),(51,0,0,0,0),(52,0,0,0,0),(53,0,0,0,0),(54,6,7,12,1);
/*!40000 ALTER TABLE `problem_contest_info` ENABLE KEYS */;
UNLOCK TABLES;

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
  `code_source` int(10) unsigned NOT NULL,
  `code_language` enum('JAVA8','CPP','C','PYTHON27','PYTHON36') NOT NULL,
  `belong` int(10) unsigned NOT NULL,
  `result` json NOT NULL COMMENT 'json array',
  `time_used` decimal(10,3) unsigned NOT NULL,
  `memory_used` decimal(10,3) unsigned NOT NULL,
  `status` enum('Accepted','WrongAnswer','TimeLimitExceeded','CompileError','RuntimeError','JudgeError') NOT NULL,
  `submit_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission`
--

LOCK TABLES `submission` WRITE;
/*!40000 ALTER TABLE `submission` DISABLE KEYS */;
INSERT INTO `submission` VALUES (1,14,15,5,'PYTHON36',0,'[{\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}]',0.020,2.980,'Accepted',1510571480286),(2,14,15,6,'PYTHON36',0,'[{\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}, {\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}, {\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}]',0.110,4.290,'RuntimeError',1510571659647),(3,14,15,7,'PYTHON36',0,'[{\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}, {\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}, {\"status\": \"RuntimeError\", \"stderr\": \"Traceback (most recent call last):\\n  File \\\"main.py\\\", line 1, in <module>\\n    while(True):  print(\'stdout\')\\nOSError: [Errno 27] File too large\\nException ignored in: <_io.TextIOWrapper name=\'<stdout>\' mode=\'w\' encoding=\'ANSI_X3.4-1968\'>\\nOSError: [Errno 27] File too large\\n\"}]',0.110,4.290,'RuntimeError',1510571855417),(4,14,15,8,'PYTHON36',0,'[{\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}]',0.020,3.000,'Accepted',1510647017054),(5,14,15,9,'PYTHON36',0,'[{\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}]',0.020,3.010,'Accepted',1510647023708),(6,14,54,10,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.000,'WrongAnswer',1510832408664),(7,14,54,11,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,2.990,'WrongAnswer',1510832619203),(8,14,54,12,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,2.970,'WrongAnswer',1510832751380),(9,14,54,13,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,2.990,'WrongAnswer',1510833023526),(10,14,54,14,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.000,'WrongAnswer',1510833257262),(11,14,54,15,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.030,'WrongAnswer',1510833536309),(12,14,54,16,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.020,'WrongAnswer',1510833623850),(13,14,54,17,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.050,'WrongAnswer',1510833767133),(14,14,54,18,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.010,'WrongAnswer',1510833814653),(15,14,54,19,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,2.950,'WrongAnswer',1510833897691),(16,14,54,20,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.030,3.000,'WrongAnswer',1510833928308),(17,14,54,21,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.010,'WrongAnswer',1510833961867),(18,14,54,22,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.000,'WrongAnswer',1510835715189),(19,14,54,23,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}]',0.020,3.050,'Accepted',1510835767413),(20,14,54,24,'PYTHON36',6,'[{\"status\": \"Accepted\"}, {\"status\": \"Accepted\"}]',0.020,3.040,'Accepted',1510836121042),(21,14,54,25,'PYTHON36',6,'[{\"status\": \"WrongAnswer\"}, {\"status\": \"WrongAnswer\"}]',0.020,3.040,'WrongAnswer',1510836256829);
/*!40000 ALTER TABLE `submission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `name` varchar(20) NOT NULL,
  `used` int(10) unsigned DEFAULT '0',
  `number` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`name`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_case`
--

DROP TABLE IF EXISTS `test_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_case` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `stdin` varchar(100) NOT NULL,
  `stdout` varchar(100) NOT NULL,
  `strength` tinyint(2) unsigned NOT NULL COMMENT '介于1-10之间',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
INSERT INTO `test_case` VALUES (8,15,'stdin','stdout',5,0),(9,15,'stdin','stdout',5,0),(11,15,'stdin','stdout',5,1510464329067),(15,54,'stdin','stdout',5,1510832251391),(16,54,'stdin','stdout',3,1510832262021);
/*!40000 ALTER TABLE `test_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `avatar` int(10) unsigned DEFAULT '0',
  `password` varchar(32) NOT NULL,
  `role` tinyint(1) unsigned DEFAULT '0' COMMENT '0 普通用户\n9 管理员',
  `permission` json NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  `contest_times` int(10) unsigned DEFAULT '0',
  `score` int(10) unsigned DEFAULT '0',
  `register_time` bigint(13) unsigned NOT NULL,
  `last_login` bigint(13) unsigned DEFAULT '0',
  `verified` tinyint(1) unsigned DEFAULT '0' COMMENT '0未验证\n1验证通过',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aef','aef',0,'aef',0,'[\"get\", \"ge\"]',0,0,0,0,12312,0,0),(3,'chen!@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625563796,0,0),(5,'chen!1@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625680241,0,0),(6,'chen!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625693663,0,0),(8,'che1n!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625797534,0,0),(9,'c1@126.com1','test',0,'123456',0,'[]',0,0,0,0,0,0,0),(10,'c1@126.com','test',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0),(11,'test@test.com2','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0),(12,'test@test.com1','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,1509886602210,0,0),(13,'test@test.com3','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,1509964177613,0,0),(14,'test@test.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',14,2,0,0,1510129937075,0,0),(15,'1510157247573@126.com','1510157247573-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510157248389,0,0),(16,'1510157414297@126.com','1510157414297-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510157414311,0,0),(17,'1510200482152@126.com','1510200482152-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510200482167,0,0),(18,'1510200639157@126.com','1510200639157-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510200639171,0,0),(19,'test@test1.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510377927655,0,0),(21,'test@test2.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510377955548,0,0),(23,'test@test99.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510377967933,0,0),(25,'test@test9.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510378047399,0,0),(27,'test@test8.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510378179880,0,0),(28,'test@test.comx','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510462603114,0,0),(29,'test@test.comxb','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510462649043,0,0),(30,'test@test.com7','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,1510464192666,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-16 20:45:02
