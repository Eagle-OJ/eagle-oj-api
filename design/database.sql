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
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `pid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `code_language` json NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `difficult` tinyint(1) unsigned NOT NULL,
  `input_format` varchar(200) NOT NULL,
  `output_format` varchar(200) NOT NULL,
  `constraint` varchar(200) NOT NULL,
  `sample` json NOT NULL,
  `moderator` json NOT NULL,
  `tag` json NOT NULL,
  `share` tinyint(1) unsigned NOT NULL COMMENT '0 自己使用\n1 审核中\n2 全局使用',
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_info`
--

DROP TABLE IF EXISTS `problem_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_info` (
  `pid` int(10) unsigned NOT NULL,
  `belong` int(10) unsigned NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_info`
--

LOCK TABLES `problem_info` WRITE;
/*!40000 ALTER TABLE `problem_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem_info` ENABLE KEYS */;
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
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_case`
--

LOCK TABLES `test_case` WRITE;
/*!40000 ALTER TABLE `test_case` DISABLE KEYS */;
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
  `role` tinyint(1) unsigned DEFAULT '0',
  `permission` json NOT NULL,
  `submit_times` int(10) unsigned DEFAULT '0',
  `accept_times` int(10) unsigned DEFAULT '0',
  `contest_times` int(10) unsigned DEFAULT '0',
  `score` int(10) unsigned DEFAULT '0',
  `register_time` bigint(13) unsigned NOT NULL,
  `last_login` bigint(13) unsigned DEFAULT '0',
  `verified` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aef','aef',0,'aef',0,'[\"get\", \"ge\"]',0,0,0,0,12312,0,0),(3,'chen!@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625563796,0,0),(5,'chen!1@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625680241,0,0),(6,'chen!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625693663,0,0),(8,'che1n!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,1509625797534,0,0),(9,'c1@126.com1','test',0,'123456',0,'[]',0,0,0,0,0,0,0),(10,'c1@126.com','test',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0),(11,'test@test.com2','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0),(12,'test@test.com1','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,1509886602210,0,0),(13,'test@test.com','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,1509964177613,0,0);
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

-- Dump completed on 2017-11-06 19:24:23
