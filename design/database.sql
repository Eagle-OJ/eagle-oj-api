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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (1,14,'123123dse',1,1510486741972),(2,14,'123123dse',1,1510486744070),(3,14,'2017/10/13/d6cb7a12-a4f6-445b-bf02-3f8511de784f.py',1,1510564350031),(4,14,'2017/10/13/cd699257-290c-4171-ba54-dbad883ab204.py',1,1510571431147),(5,14,'2017/10/13/e7a2c2f6-ca0b-4406-8daf-1b4b33da4514.py',1,1510571480147),(6,14,'2017/10/13/c9350af9-ca10-42fd-a090-c225756d79a8.py',1,1510571633307),(7,14,'2017/10/13/5abf6874-3282-4798-aad5-63d3a7071d89.py',1,1510571829273),(8,14,'2017/10/14/791c98cb-4a81-49c7-a07d-e5283ee1c19e.py',1,1510647016892),(9,14,'2017/10/14/25a4c4f8-e520-4284-ab58-8f8b4b62a5d9.py',1,1510647023431),(10,14,'2017/10/16/c55be3cf-a643-4f1d-be05-e04413d004c7.py',1,1510832409352),(11,14,'2017/10/16/bf3699b3-2f0b-4b4a-97b4-8cb1d6cb5c66.py',1,1510832619465),(12,14,'2017/10/16/fcdf88b6-b9ac-4e6f-9fd7-da9b5d9677a6.py',1,1510832751645),(13,14,'2017/10/16/503c9554-af52-41ef-ac17-ec6c267da7ee.py',1,1510833023748),(14,14,'2017/10/16/c704a75e-1ee3-4c22-852b-c41ca0c2d838.py',1,1510833257525),(15,14,'2017/10/16/6ef2c421-56e9-48f2-b059-c2a2e2e7cd1e.py',1,1510833536594),(16,14,'2017/10/16/d6284e97-1906-4f3a-97f0-f2e82e7f8750.py',1,1510833624006),(17,14,'2017/10/16/de5d284a-5fe5-4735-ae9e-2d47264548d8.py',1,1510833767319),(18,14,'2017/10/16/105e0bee-8377-4e2c-be17-d4252c11688a.py',1,1510833814878),(19,14,'2017/10/16/4a4b7a54-0586-4d18-8185-bcb198942adb.py',1,1510833897877),(20,14,'2017/10/16/9cf73a09-a74d-40b0-a7a5-e9938f602e80.py',1,1510833928528),(21,14,'2017/10/16/014b5e6e-88c8-4094-96b5-914991c601b9.py',1,1510833962117),(22,14,'2017/10/16/4c076b51-db6c-46c2-8e99-0737e3373651.py',1,1510835715417),(23,14,'2017/10/16/b51a79a5-cf1c-46d5-8ffa-415034674edb.py',1,1510835767436),(24,14,'2017/10/16/683f3f49-881e-42c5-8e37-fd8a3b6ff3e9.py',1,1510836121338),(25,14,'2017/10/16/ecd46388-e905-465f-88c5-971ee95f6aed.py',1,1510836256961),(26,35,'2017/10/29/2b9a585d-e603-43d5-be33-5476006a5288.jpg',1,1511956980040),(27,35,'/2017/10/29/edce59d5-1577-4632-a213-a6281b0b9777.jpg',1,1511957315739),(28,35,'/2017/10/29/ae9c7dd0-84ad-451f-8170-ec31d8e9647d.jpg',1,1511957332535),(29,35,'/2017/10/29/10bfeeff-3d21-464f-9aa2-c4725d2830dc.jpg',1,1511957382523),(30,35,'/2017/10/29/2a815761-2cdb-41ee-88d8-879799730050.jpg',1,1511957397393),(31,35,'/2017/10/29/7e7a3291-b7e0-4a8a-a4be-5c09cc8a1962.jpg',1,1511957403053),(32,1,'testurl',1,1512302834899);
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
  `total_time` bigint(13) unsigned DEFAULT NULL COMMENT '大于0则代表倒计时类型考试',
  `password` char(6) DEFAULT NULL,
  `official` tinyint(1) unsigned NOT NULL COMMENT '0非正式\n1官方',
  `type` tinyint(1) unsigned NOT NULL COMMENT '模式\n0普通模式\n1ACM模式\n时间\n0 普通模式 开始到结束时间\n1 限时模式\n0: 0+0\n1:0+1\n2:1+0\n3:1+1',
  `status` tinyint(1) unsigned NOT NULL COMMENT '0编辑中 1开启 2已结束',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest`
--

LOCK TABLES `contest` WRITE;
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
INSERT INTO `contest` VALUES (3,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,1515427200000,5,NULL,1,1,1,1510403394474),(4,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',150000000000000,150000000000100,5,'1',0,1,1,1510403786772),(5,'这是一个比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',1400000000000,1400000000000,5,'1',0,1,2,1510464222315),(6,'这是一个牛逼的比赛',14,'检测大家的技巧','我来给大家信息接发接飞机发哦飞啊飞阿尔及发级啊诶发骄傲二维if就as诶飞',1612313440000,1712313440000,5,'1',0,0,1,1510832002472),(7,'aef',35,'aef','aef',1512313440000,1516118400000,NULL,NULL,0,0,0,1512293375827),(8,'再来试试11',35,'哈哈哈','哈哈',1512316800000,1515427200000,1512238500000,'123456',0,1,2,1512295979305);
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
  `cid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
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
-- Dumping data for table `contest_user_info`
--

LOCK TABLES `contest_user_info` WRITE;
/*!40000 ALTER TABLE `contest_user_info` DISABLE KEYS */;
INSERT INTO `contest_user_info` VALUES (1,12,0,0,0,0,0,0,1510559765984),(3,14,0,0,0,0,0,0,1510403705466),(4,14,0,0,0,0,0,0,1510403804620),(6,14,9,1,0,0,0,0,1510832042252),(8,35,0,0,0,0,0,0,1512564466260);
/*!40000 ALTER TABLE `contest_user_info` ENABLE KEYS */;
UNLOCK TABLES;

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
  `people` int(10) unsigned DEFAULT '0',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,14,'我们是胜利之队','123sda',0,1510486465720),(2,35,'aefasefae',NULL,0,1512473508555),(3,35,'aefasefae',NULL,0,1512473515209),(4,35,'aefasefae',NULL,0,1512473548482),(5,35,'aefasefae',NULL,0,1512473568962),(6,35,'123312',NULL,0,1512473592860),(7,35,'123312','234',0,1512473597193),(8,35,'123312',NULL,0,1512473604787),(9,35,'awefaf',NULL,0,1512474297647),(10,35,'ergrseg',NULL,0,1512478665453),(11,35,'awsdrgxcvsdzvsdvsrv',NULL,0,1512478697313);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_user_info`
--

DROP TABLE IF EXISTS `group_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_user_info` (
  `gid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
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
  `description` json NOT NULL,
  `input_format` json NOT NULL,
  `output_format` json NOT NULL,
  `difficult` tinyint(1) unsigned NOT NULL COMMENT '0:easy 1:middle 2:difficult 3:expert',
  `samples` json NOT NULL COMMENT 'json array\n[{input: 1, output:10}, {...}]',
  `moderators` json NOT NULL COMMENT 'json array\n[1,2,3,54]',
  `submit_times` int(10) unsigned DEFAULT '0',
  `used_times` int(10) DEFAULT '0' COMMENT '被别人引用过的次数',
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  `status` tinyint(1) unsigned NOT NULL COMMENT '0 自己使用\n1 审核中\n2 全局使用',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (1,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1]',0,NULL,0,0,0,0,0,1,1510133001856),(3,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510137594057),(4,1,'awefasefasef','[1, 2]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2]',0,NULL,0,0,0,0,0,1,1510155806317),(5,1,'awefasefasef','[1, 2]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2]',0,NULL,0,0,0,0,0,1,1510155937092),(6,1,'awefasefasef','[1, 2]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2]',0,NULL,0,0,0,0,0,1,1510155974292),(7,1,'awefasefasef','[1, 2]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2]',0,NULL,0,0,0,0,0,2,1510155974906),(8,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510203568179),(9,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510207430914),(10,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510207555221),(11,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510207578307),(12,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510208130947),(13,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510379347391),(14,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510379353632),(15,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2, 3]',0,NULL,0,0,0,0,0,2,1510464328957),(16,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510462910373),(19,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510463003126),(21,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464252616),(22,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464328801),(23,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464352016),(24,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464352748),(25,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464353151),(26,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464353514),(27,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464353833),(28,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464354119),(29,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464354529),(30,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464354753),(31,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464355256),(32,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464355572),(33,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464355997),(34,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464356215),(35,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464356483),(36,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464356664),(37,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464356957),(38,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464357246),(39,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464357562),(40,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464357881),(41,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464358221),(42,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464358577),(43,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464358812),(44,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464359094),(45,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464359277),(46,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464359531),(47,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464359751),(48,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464365334),(49,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464365742),(50,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464365982),(51,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464366273),(52,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,NULL,0,0,0,0,0,1,1510464384639),(53,1,'awefasefasef','[1, 2]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[1, 2]',0,NULL,0,0,0,0,0,1,1510465276410),(54,14,'awefasefasef','[1, 3, 4]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',14,NULL,2,0,0,0,0,1,1510832220276),(55,35,'awefasefasef','[]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,0,0,0,0,0,0,0,1512032505885),(56,35,'WD','[]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,0,0,0,0,0,0,0,1512038335822),(57,35,'awefasefasef','[]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfas\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasefeasfefe\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[]',0,0,0,0,0,0,0,2,1512038425558),(58,35,'awefasefasef','[35, 36, 37]','{\"ops\": [{\"insert\": \"aefaesfasf\\n\"}]}','{\"ops\": [{\"insert\": \"aserfgawf\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfasef\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"ef\"}]','[35, 37, 12]',0,0,0,0,0,0,0,2,1512039261743),(59,35,'如果这是代还','[]','{\"ops\": [{\"insert\": \"a+bproblems\\n\"}]}','{\"ops\": [{\"insert\": \"asefasfeasfaefasef\\n\"}]}','{\"ops\": [{\"insert\": \"aesfasefasef\\n\"}]}',0,'[{\"input\": \"abc\", \"output\": \"c\"}]','[]',0,0,0,0,0,0,0,2,1512276517458),(60,35,'aefasefawe','[]','{\"ops\": [{\"insert\": \"WDAEFASF\\n\"}]}','{\"ops\": [{\"insert\": \"aefasf\\n\"}]}','{\"ops\": [{\"insert\": \"aewfasefasef\\n\"}]}',0,'[{\"input\": \"awefe\", \"output\": \"asfasef\"}]','[]',0,0,0,0,0,0,0,2,1512377633934);
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
  `ac_times` int(10) unsigned DEFAULT '0',
  `wa_times` int(10) unsigned DEFAULT '0',
  `rte_times` int(10) unsigned DEFAULT '0',
  `tle_times` int(10) unsigned DEFAULT '0',
  `ce_times` int(10) unsigned DEFAULT '0',
  UNIQUE KEY `pid_cid_unique` (`pid`,`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_contest_info`
--

LOCK TABLES `problem_contest_info` WRITE;
/*!40000 ALTER TABLE `problem_contest_info` DISABLE KEYS */;
INSERT INTO `problem_contest_info` VALUES (3,0,0,0,0,0,0,0,0),(4,0,0,0,0,0,0,0,0),(5,0,0,0,0,0,0,0,0),(6,0,0,0,0,0,0,0,0),(7,0,0,0,0,0,0,0,0),(8,0,0,0,0,0,0,0,0),(9,0,0,0,0,0,0,0,0),(10,0,0,0,0,0,0,0,0),(11,0,0,0,0,0,0,0,0),(12,0,0,0,0,0,0,0,0),(13,0,0,0,0,0,0,0,0),(14,0,0,0,0,0,0,0,0),(15,0,0,2,2,0,0,0,0),(15,4,5,0,0,0,0,0,0),(21,0,0,0,0,0,0,0,0),(22,0,0,0,0,0,0,0,0),(23,0,0,0,0,0,0,0,0),(24,0,0,0,0,0,0,0,0),(25,0,0,0,0,0,0,0,0),(26,0,0,0,0,0,0,0,0),(27,0,0,0,0,0,0,0,0),(28,0,0,0,0,0,0,0,0),(29,0,0,0,0,0,0,0,0),(30,0,0,0,0,0,0,0,0),(31,0,0,0,0,0,0,0,0),(32,0,0,0,0,0,0,0,0),(33,0,0,0,0,0,0,0,0),(34,0,0,0,0,0,0,0,0),(35,0,0,0,0,0,0,0,0),(36,0,0,0,0,0,0,0,0),(37,0,0,0,0,0,0,0,0),(38,0,0,0,0,0,0,0,0),(39,0,0,0,0,0,0,0,0),(40,0,0,0,0,0,0,0,0),(41,0,0,0,0,0,0,0,0),(42,0,0,0,0,0,0,0,0),(43,0,0,0,0,0,0,0,0),(44,0,0,0,0,0,0,0,0),(45,0,0,0,0,0,0,0,0),(46,0,0,0,0,0,0,0,0),(47,0,0,0,0,0,0,0,0),(48,0,0,0,0,0,0,0,0),(49,0,0,0,0,0,0,0,0),(50,0,0,0,0,0,0,0,0),(51,0,0,0,0,0,0,0,0),(52,0,0,0,0,0,0,0,0),(53,0,0,0,0,0,0,0,0),(54,6,7,12,1,0,0,0,0),(57,8,4,0,0,0,0,0,0),(58,8,5,0,0,0,0,0,0),(59,8,8,0,0,0,0,0,0),(60,8,1,0,0,0,0,0,0);
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
-- Dumping data for table `tag_problem`
--

LOCK TABLES `tag_problem` WRITE;
/*!40000 ALTER TABLE `tag_problem` DISABLE KEYS */;
INSERT INTO `tag_problem` VALUES (1,60),(4,60);
/*!40000 ALTER TABLE `tag_problem` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'java',3),(2,'python',4),(3,'hellworld',4),(4,'链表',3);
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_cases`
--

LOCK TABLES `test_cases` WRITE;
/*!40000 ALTER TABLE `test_cases` DISABLE KEYS */;
INSERT INTO `test_cases` VALUES (8,15,'stdin','stdout',5,0),(9,15,'stdin','stdout',5,0),(11,15,'stdin','stdout',5,1510464329067),(15,54,'stdin','stdout',5,1510832251391),(16,54,'stdin','stdout',3,1510832262021),(20,58,'大海','而发而非啊',9,1512096555464),(23,60,'dfsv','sadv',3,1512391303773);
/*!40000 ALTER TABLE `test_cases` ENABLE KEYS */;
UNLOCK TABLES;

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
  `real_name` varchar(20) DEFAULT NULL,
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
  `score` int(10) unsigned DEFAULT '0',
  `gender` tinyint(1) unsigned DEFAULT '0' COMMENT '0保密\n1男\n2女',
  `motto` varchar(50) DEFAULT NULL,
  `register_time` bigint(13) unsigned NOT NULL,
  `verified` tinyint(1) unsigned DEFAULT '0' COMMENT '0未验证\n1验证通过',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aef','aef',NULL,0,'aef',0,'[\"get\", \"ge\"]',0,0,0,0,0,0,0,0,0,0,NULL,12312,0),(3,'chen!@126.com','smith',NULL,0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509625563796,0),(5,'chen!1@126.com','smith',NULL,0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509625680241,0),(6,'chen!11@126.com','smith',NULL,0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509625693663,0),(8,'che1n!11@126.com','smith',NULL,0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509625797534,0),(9,'c1@126.com1','test',NULL,0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,0,0),(10,'c1@126.com','test',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,0,0),(11,'test@test.com2','I Am Test',NULL,0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,0,0),(12,'test@test.com1','I Am Test',NULL,0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509886602210,0),(13,'test@test.com3','I Am Test',NULL,0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1509964177613,0),(14,'test@test.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',14,0,2,0,0,0,0,0,0,0,NULL,1510129937075,0),(15,'1510157247573@126.com','1510157247573-TESTER',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510157248389,0),(16,'1510157414297@126.com','1510157414297-TESTER',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510157414311,0),(17,'1510200482152@126.com','1510200482152-TESTER',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510200482167,0),(18,'1510200639157@126.com','1510200639157-TESTER',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510200639171,0),(19,'test@test1.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510377927655,0),(21,'test@test2.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510377955548,0),(23,'test@test99.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510377967933,0),(25,'test@test9.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510378047399,0),(27,'test@test8.com','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510378179880,0),(28,'test@test.comx','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510462603114,0),(29,'test@test.comxb','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510462649043,0),(30,'test@test.com7','I am tester',NULL,0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1510464192666,0),(31,'fe@12.dth','asef',NULL,0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511771980307,0),(32,'chen1@126.com','abc',NULL,0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511772230886,0),(33,'chen@126.com','abc',NULL,0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511772355487,0),(34,'1@126.com','aef',NULL,0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511772618636,0),(35,'1@1.com','Smith代还','打败',31,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,2,'我是孤独求败的人',1511772653407,0),(36,'12@1.com','aec',NULL,0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511772836330,0),(37,'2@2.com','大海',NULL,0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511772924348,0),(38,'danny@126.com','danny',NULL,0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1511774201713,0),(39,'dfgv@12.com','aewfeasf',NULL,0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,0,NULL,1512306366983,0);
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

-- Dump completed on 2017-12-06 21:10:22
