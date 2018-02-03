CREATE DATABASE  IF NOT EXISTS `eagle_oj` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eagle_oj`;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (2,'123','123',1516174781770),(3,'xxx','xxxxx',1516180335871),(5,'hello','xxx',1516858670847);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (1,14,'123123dse',1,1510486741972),(2,14,'123123dse',1,1510486744070),(3,14,'2017/10/13/d6cb7a12-a4f6-445b-bf02-3f8511de784f.py',1,1510564350031),(4,14,'2017/10/13/cd699257-290c-4171-ba54-dbad883ab204.py',1,1510571431147),(5,14,'2017/10/13/e7a2c2f6-ca0b-4406-8daf-1b4b33da4514.py',1,1510571480147),(6,14,'2017/10/13/c9350af9-ca10-42fd-a090-c225756d79a8.py',1,1510571633307),(7,14,'2017/10/13/5abf6874-3282-4798-aad5-63d3a7071d89.py',1,1510571829273),(8,14,'2017/10/14/791c98cb-4a81-49c7-a07d-e5283ee1c19e.py',1,1510647016892),(9,14,'2017/10/14/25a4c4f8-e520-4284-ab58-8f8b4b62a5d9.py',1,1510647023431),(10,14,'2017/10/16/c55be3cf-a643-4f1d-be05-e04413d004c7.py',1,1510832409352),(11,14,'2017/10/16/bf3699b3-2f0b-4b4a-97b4-8cb1d6cb5c66.py',1,1510832619465),(12,14,'2017/10/16/fcdf88b6-b9ac-4e6f-9fd7-da9b5d9677a6.py',1,1510832751645),(13,14,'2017/10/16/503c9554-af52-41ef-ac17-ec6c267da7ee.py',1,1510833023748),(14,14,'2017/10/16/c704a75e-1ee3-4c22-852b-c41ca0c2d838.py',1,1510833257525),(15,14,'2017/10/16/6ef2c421-56e9-48f2-b059-c2a2e2e7cd1e.py',1,1510833536594),(16,14,'2017/10/16/d6284e97-1906-4f3a-97f0-f2e82e7f8750.py',1,1510833624006),(17,14,'2017/10/16/de5d284a-5fe5-4735-ae9e-2d47264548d8.py',1,1510833767319),(18,14,'2017/10/16/105e0bee-8377-4e2c-be17-d4252c11688a.py',1,1510833814878),(19,14,'2017/10/16/4a4b7a54-0586-4d18-8185-bcb198942adb.py',1,1510833897877),(20,14,'2017/10/16/9cf73a09-a74d-40b0-a7a5-e9938f602e80.py',1,1510833928528),(21,14,'2017/10/16/014b5e6e-88c8-4094-96b5-914991c601b9.py',1,1510833962117),(22,14,'2017/10/16/4c076b51-db6c-46c2-8e99-0737e3373651.py',1,1510835715417),(23,14,'2017/10/16/b51a79a5-cf1c-46d5-8ffa-415034674edb.py',1,1510835767436),(24,14,'2017/10/16/683f3f49-881e-42c5-8e37-fd8a3b6ff3e9.py',1,1510836121338),(25,14,'2017/10/16/ecd46388-e905-465f-88c5-971ee95f6aed.py',1,1510836256961),(26,35,'2017/10/29/2b9a585d-e603-43d5-be33-5476006a5288.jpg',1,1511956980040),(27,35,'/2017/10/29/edce59d5-1577-4632-a213-a6281b0b9777.jpg',1,1511957315739),(28,35,'/2017/10/29/ae9c7dd0-84ad-451f-8170-ec31d8e9647d.jpg',1,1511957332535),(29,35,'/2017/10/29/10bfeeff-3d21-464f-9aa2-c4725d2830dc.jpg',1,1511957382523),(30,35,'/2017/10/29/2a815761-2cdb-41ee-88d8-879799730050.jpg',1,1511957397393),(31,35,'/2017/10/29/7e7a3291-b7e0-4a8a-a4be-5c09cc8a1962.jpg',1,1511957403053),(32,1,'testurl',1,1512302834899),(33,35,'/2017/11/10/a688b56e-0d21-43c7-a0db-00b22dfd3c62.py',1,1512889441047),(34,35,'/2017/11/10/74e927d8-6d60-4f44-b9e6-973dbfdfbb5b.py',1,1512889563671),(35,35,'/2017/11/10/60888e0a-808e-4924-9d20-21b94565f67f.py',1,1512889640689),(36,35,'/2017/11/10/6f343975-f3a0-408a-abb3-e1164ecffd4d.py',1,1512889837974),(37,35,'/2017/11/10/786fc479-a448-499d-b3b2-1e05123d085c.py',1,1512890081300),(38,35,'/2017/11/10/5336900b-8e0a-487a-9d5f-39974cf32c08.py',1,1512890093395),(39,35,'/2017/11/10/4039d485-dd68-4f70-a582-1f311f4a71b2.py',1,1512890122654),(40,35,'/2017/11/10/2a9564e8-fc36-4843-88bb-b834fe82305c.py',1,1512893628781),(41,35,'/2017/11/10/d67fac02-8718-4e50-a6e9-3be9810cab90.py',1,1512893939188),(42,35,'/2017/11/10/3e219d30-6efd-4dea-8eb4-420fe3490541.py',1,1512893979315),(43,35,'/2017/11/10/67b9017f-03a9-4d65-9712-3f50ebfa5ba5.py',1,1512893979718),(44,35,'/2017/11/10/f7f26ac1-2ec5-4b3c-a633-607a54fc1f1b.py',1,1512893979990),(45,35,'/2017/11/10/85e8f42e-86e3-4eb2-9ca9-606bacdba454.py',1,1512893982527),(46,35,'/2017/11/10/7da81283-8d89-453f-9a8d-afeb005243a7.py',1,1512893992944),(47,35,'/2017/11/10/f07ec6de-64f5-4976-99b2-0273c7523520.py',1,1512893993759),(48,35,'/2017/11/10/22ed4b0e-db17-4a0c-bbf6-635c40cb157f.py',1,1512893994421),(49,35,'/2017/11/10/e862c37a-14a6-4850-9b7c-6b8f56d5db17.py',1,1512894300087),(50,35,'/2017/11/11/782d4872-5cdb-4f2d-8b37-5951000b8135.py',1,1512962187286),(51,35,'/2017/11/11/48cafabd-4020-49d2-af77-13d7072e92c8.py',1,1512962310298),(52,35,'/2017/11/11/537d34d9-91fb-4e57-be3f-40e66435d49b.py',1,1512963061617),(53,35,'/2017/11/11/f56f6e93-41cd-46b9-9f11-0f8595064e1a.py',1,1512963126150),(54,35,'/2017/11/11/111c8334-22ed-4491-8a91-21ab0e98c1e1.py',1,1512963226267),(55,35,'/2017/11/11/04ca4cf9-477d-4a42-ac10-121279fb7bf5.py',1,1512963283076),(56,35,'/2017/11/13/cffab29d-b13f-48c0-9107-6956ac468f10.py',1,1513158419607),(57,35,'/2017/11/13/4bcafb01-2a29-403c-8091-c93a9482819c.py',1,1513158778511),(58,35,'/2017/11/15/7d4d9ca1-8ba8-4fa5-964f-02e939a7d199.c',1,1513305875037),(59,35,'/2017/11/16/cbf6dd5e-fb51-409f-bf2a-d7dab34b8507.java',1,1513436332352),(60,35,'/2017/11/16/61639fc5-120a-40c1-8bda-92e80a45b1ec.java',1,1513436345724),(61,35,'/2017/11/16/7cd6bdc0-a6c6-4ce8-ab45-bc2ac83af58d.java',1,1513436436163),(62,35,'/2017/11/16/e52a0f05-553b-4ffd-91cf-80bd5812a89c.c',1,1513436804974),(63,35,'/2017/11/18/01d27425-c966-4afa-ac5f-f1ae0b9ad490.java',1,1513565329101),(64,35,'/2017/11/18/302864c6-8796-49cc-84b7-9cd1e022fbc7.java',1,1513565426871),(65,35,'/2017/11/18/6729444f-42b1-4ebb-b809-569931fae381.java',1,1513565468590),(66,35,'/2017/11/18/846bdb00-4bec-4c65-82b6-ec6da5845fbf.c',1,1513565493604),(67,35,'/2017/11/18/2a24ad4e-c5ad-46da-af84-e20578f48396.py',1,1513566046970),(68,35,'/2017/11/18/6d5da777-cc74-4d15-941b-03fede8baa19.py',1,1513566157698),(69,35,'/2017/11/18/c416b296-7c90-43db-83d7-acdf9f1061c4.py',1,1513566193893),(70,35,'/2017/11/18/01ae2eae-f1c4-452e-9185-174e8863e690.py',1,1513572066086),(71,35,'/2017/11/18/87344467-eaee-48c7-8dc6-3f1f4ba60347.java',1,1513588807494),(72,35,'/2017/11/18/97ea6603-b1f6-4273-9c7c-d133a964569f.java',1,1513589002931),(73,35,'/2017/11/18/d4f2d8fc-6e7e-4e33-98bb-73fd02b0112e.java',1,1513589055850),(74,35,'/2017/11/18/efa6ceae-5c44-46db-9eda-ca680e865db7.java',1,1513589145888),(75,35,'/2017/11/18/8f3d6da4-45d5-4d9b-a837-41d86a0e5a9b.java',1,1513589198309),(76,35,'/2017/11/18/8fc5722b-1c51-4875-98bd-fd85e5854a75.py',1,1513590336327),(77,35,'/2017/11/18/438be04a-498d-4a39-971b-b391ea90326a.py',1,1513590853229),(78,37,'/2017/11/18/cd632d25-420f-4352-b4dd-0f70d375d5e1.c',1,1513594238891),(79,35,'/2017/11/21/3014d7fd-dded-4a1d-856b-29a4acec2b99.c',1,1513860193465),(80,35,'/2017/11/21/70cdc952-1e28-4083-825b-3b47ad28ae75.py',1,1513860339210),(81,35,'/2017/11/22/2ab1baa4-fe1c-4c76-909f-2ceb64e054e9.c',1,1513911475988),(82,35,'/2017/11/22/3946f168-aa43-480b-965d-70f444ba80b3.c',1,1513911764050),(83,35,'/2017/11/28/9cb2fbf4-f781-42ac-b7f8-3fe68965ebcd.cpp',1,1514445735323),(84,35,'/2017/11/28/12284341-4edf-424c-846f-96e3e86cb95b.cpp',1,1514445772359),(85,35,'/2018/0/20/e0ac4162-778a-4921-9666-a5cd216aa51e.jpg',1,1516379329202),(86,35,'/2018/0/20/5e829692-7bed-463d-ab21-5739fb417821.jpg',1,1516379350327),(87,35,'/2018/0/22/753f3c88-91b3-41b2-b323-7fb79c7f361b.jpg',1,1516624388321),(88,35,'/2018/0/27/0683ed68-6dfb-493e-b4a4-95cc7215276a.java',1,1517023032451),(89,35,'/2018/0/27/021c0391-61f5-4e8c-9b2e-222634988f75.java',1,1517023084227),(90,35,'/2018/0/27/45e911c1-3e3f-4cf3-b378-f42b7085ea7d.java',1,1517023240546),(91,35,'/2018/0/27/e0525f1e-d014-4baa-80e5-581257866eed.py',1,1517023369201),(92,35,'/2018/0/27/0762b1c9-cd67-437e-943e-24f9b376c9d1.py',1,1517023455759),(93,35,'/2018/0/27/a8e33532-4501-44ff-9e25-4b5a7def4392.py',1,1517023553959),(94,35,'/2018/0/27/dede96b3-bf30-45e8-8bf6-0199ed737e45.jpg',1,1517030767309),(95,35,'/2018/0/27/3b15067e-d26d-4696-857d-286acb03e52e.jpg',1,1517030860744),(96,35,'/2018/0/27/a9579ee8-9004-4bc6-9b07-0722c4e008a4.jpg',1,1517031494272),(97,35,'/2018/0/28/8fa8dcb4-e6d8-4546-840f-e094637a1b89.py',1,1517146525038),(99,35,'/2018/0/30/5f5f4218-433e-421b-9991-5bd15774fa83.java',1,1517322581200),(101,35,'/2018/0/30/15719834-5b38-4677-8b4e-fb40159339f2.java',1,1517324029220),(105,35,'/2018/0/30/f26e2422-7e8b-4890-901b-b7a6be47ceed.java',1,1517326544705),(106,35,'/2018/0/30/6eace0e9-8fe4-4c41-a38d-63f917ee1800.java',1,1517326573188),(107,35,'/2018/0/30/e4fa908b-4cbf-45f9-a108-cb47f722ec06.java',1,1517326795307),(108,35,'/2018/0/30/329c3b75-6341-4b70-a344-fc0ca07e0da7.java',1,1517326843784),(109,35,'/2018/0/30/629f7620-794e-4a8e-a2c6-81f120bf7f28.py',1,1517326869707),(110,35,'/2018/0/30/e18b4a9b-b516-4277-bdc6-cccc280a61db.py',1,1517326893170),(111,35,'/2018/0/31/621770f0-2756-45e5-b6f5-e1d3b9e43646.py',1,1517370258356),(112,35,'/2018/0/31/acdbfa29-a8b5-4c4a-b52f-da71b0a84432.py',1,1517370315404),(113,35,'/2018/0/31/ea4095b0-54fa-40d4-984b-e31e57a361f4.py',1,1517373366892),(114,35,'/2018/0/31/54fc4525-cd12-435b-8dd2-2411498f320e.py',1,1517373390516),(115,35,'/2018/0/31/dce69194-d1c4-43fe-9379-7c62cbbc08cb.py',1,1517379507702),(116,35,'/2018/0/31/75339efc-7c39-40cc-9ec7-22e7f09811ab.py',1,1517379554197),(117,35,'/2018/0/31/04062308-ec02-43f8-97e5-25f68e0fea08.py',1,1517379568603),(118,35,'/2018/0/31/eedeaa58-4c73-497f-854a-b09e626d1e5e.py',1,1517379843827),(119,35,'/2018/0/31/634fb877-b956-44c7-accf-0b3bb8fb4f93.java',1,1517381043099),(120,35,'/2018/0/31/f49c0123-5952-4589-a768-d5f198303899.java',1,1517381122611),(121,35,'/2018/0/31/6ab95070-a206-408d-81cd-809a66e54a3b.java',1,1517381398446),(122,35,'/2018/0/31/372e1b1f-bb5a-45e1-9672-9152374dff6e.java',1,1517381421033),(123,35,'/2018/0/31/42b6d139-46fb-430c-9a5f-1741490aa95b.py',1,1517382080799),(124,35,'/2018/0/31/2fc9be6b-11c4-4dc7-9d87-032a3f42be4e.py',1,1517382272250),(125,37,'/2018/0/31/1947bcd4-b6d2-41c0-bb07-5c46478af520.py',1,1517383373197),(126,37,'/2018/0/31/a9a6f6b4-f822-44f8-a7e4-dbeba0811ad3.c',1,1517383529584),(127,37,'/2018/0/31/3ab567fd-fa85-4585-9fbe-02aa62c64dc3.py',1,1517383545052),(128,37,'/2018/0/31/521cd829-23aa-4821-9d31-09d0c71fb2c9.py',1,1517396754552),(129,37,'/2018/0/31/30a2e356-c932-4085-91de-c79487ac9a1b.java',1,1517397232391),(130,35,'/2018/0/31/ac4b62fc-86ca-4ec3-9172-ea1f86979e6c.py',1,1517397832612),(131,35,'/2018/1/1/91808d93-d946-4116-80fb-315d9d6fbc34.jpg',1,1517471230870),(132,1,'/2018/1/2/0e63f736-f996-4199-a7ca-8b458ce8c7b6.jpg',1,1517541115520);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest`
--

LOCK TABLES `contest` WRITE;
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
INSERT INTO `contest` VALUES (1,'红鹰杯1',35,0,'一起来红颜被吧','这是大红鹰的挑战书',1512745500000,1513353600000,NULL,NULL,0,2,2,1512638522358),(2,'红鹰杯2',35,0,'红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2','红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2红鹰杯2',1512658800000,1513267200000,720000000,'123456',0,3,2,1512648212424),(3,'test',35,0,'啊哈哈','而发而非非法',1513699200000,1513872000000,3600000,NULL,0,3,2,1512727885637),(4,'时间测试',35,0,'good','分',1513785600000,1513872000000,12600000,NULL,0,1,0,1512731412243),(5,'新的比赛',35,0,'新的比赛新的比赛新的比赛新的比赛1','新的比赛',1516982400000,1517328000000,NULL,NULL,1,0,2,1513586277893),(6,'getGroup',35,0,'getGroup','getGroup',1517068800000,1517155200000,NULL,NULL,0,0,0,1517128028552),(7,'aef',35,0,'aefa','feaf',1517068800000,1517155200000,NULL,NULL,0,0,0,1517128249969),(8,'aef',35,0,'afasf','aefasfea',1517068800000,1517155200000,7200000,NULL,0,1,2,1517128810544),(9,'小组赛',35,1,'aef','aef',1517155200000,1517241600000,NULL,NULL,0,0,2,1517193397240),(10,'OI比赛模式测试',35,0,'this is oi test','this is oi testthis is oi test',1517328000000,1517500800000,NULL,NULL,0,0,2,1517369652149),(11,'ACM比赛模式测试',35,0,'ACM比赛模式测试ACM比赛模式测试','ACM比赛模式测试ACM比赛模式测试',1517328000000,1517500800000,NULL,NULL,0,2,2,1517379118225),(12,'比赛小组',35,5,'比赛小组比赛小组','比赛小组比赛小组',1517328000000,1517500800000,NULL,NULL,0,0,2,1517380195647);
/*!40000 ALTER TABLE `contest` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `contest_problem`
--

LOCK TABLES `contest_problem` WRITE;
/*!40000 ALTER TABLE `contest_problem` DISABLE KEYS */;
INSERT INTO `contest_problem` VALUES (1,1,4,1,0,0,0,0,0,0),(2,1,1,1,6,0,0,3,3,0),(3,1,1,10,0,0,0,0,0,0),(5,1,4,10,8,2,1,0,0,5),(8,1,1,1,1,0,0,1,0,0),(11,1,2,8,1,0,0,0,0,1),(1,2,2,1,0,0,0,0,0,0),(2,2,2,1,0,0,0,0,0,0),(3,2,2,5,0,0,0,0,0,0),(5,2,2,20,2,1,1,0,0,0),(10,2,2,20,1,0,0,0,0,1),(11,2,4,6,0,0,0,0,0,0),(1,3,3,1,0,0,0,0,0,0),(2,3,3,1,0,0,0,0,0,0),(3,3,3,1,0,0,0,0,0,0),(5,3,3,15,2,0,1,1,0,0),(8,3,5,1,0,0,0,0,0,0),(10,4,3,50,1,1,0,0,0,0),(8,6,3,5,0,0,0,0,0,0),(12,11,2,10,0,0,0,0,0,0),(10,13,1,20,4,2,2,0,0,0),(11,13,1,50,5,3,2,0,0,0),(12,13,1,50,6,1,0,1,0,4);
/*!40000 ALTER TABLE `contest_problem` ENABLE KEYS */;
UNLOCK TABLES;

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
  `score` tinyint(3) unsigned NOT NULL,
  `status` enum('AC','WA','RTE','TLE','CE') NOT NULL,
  `solved_time` bigint(13) unsigned NOT NULL,
  `used_time` bigint(13) unsigned NOT NULL,
  UNIQUE KEY `cid_pid_uid_unique` (`cid`,`pid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contest_problem_user`
--

LOCK TABLES `contest_problem_user` WRITE;
/*!40000 ALTER TABLE `contest_problem_user` DISABLE KEYS */;
INSERT INTO `contest_problem_user` VALUES (2,1,35,0,'TLE',0,0),(5,1,35,10,'AC',1513911764383,39764383),(5,1,37,10,'AC',1513594239130,0),(5,2,35,20,'AC',1513860339393,74739393),(5,2,37,3,'WA',0,0),(5,3,35,0,'RTE',0,0),(8,1,35,0,'RTE',0,0),(10,2,37,0,'CE',0,0),(10,4,37,50,'AC',1517383545111,55545111),(10,13,35,20,'AC',1517373366928,45366928),(10,13,37,2,'WA',0,0),(11,1,37,0,'CE',0,0),(11,13,35,50,'AC',1517379843921,51843921),(11,13,37,50,'AC',1517396754654,68754654),(12,13,35,50,'AC',1517382272371,54272371);
/*!40000 ALTER TABLE `contest_problem_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contest_user`
--

DROP TABLE IF EXISTS `contest_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest_user` (
  `cid` int(10) unsigned NOT NULL,
  `uid` int(10) unsigned NOT NULL,
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
-- Dumping data for table `contest_user`
--

LOCK TABLES `contest_user` WRITE;
/*!40000 ALTER TABLE `contest_user` DISABLE KEYS */;
INSERT INTO `contest_user` VALUES (1,35,0,0,0,0,0,0,0,1516011040650),(2,35,7,0,0,0,3,3,0,1512726258550),(3,35,0,0,0,0,0,0,0,1513766993861),(4,35,0,0,0,0,0,0,0,1513859188601),(5,35,11,2,2,3,1,0,5,1513587153645),(5,37,1,1,1,0,0,0,0,1513594201072),(8,35,1,0,0,0,1,0,0,1517144905961),(9,35,0,0,0,0,0,0,0,1517212710739),(10,35,3,2,2,1,0,0,0,1517369813806),(10,37,3,1,1,1,0,0,1,1517383363546),(11,35,4,2,2,2,0,0,0,1517379457821),(11,37,2,1,1,0,0,0,1,1517395072738),(12,35,6,1,1,0,1,0,4,1517381035276);
/*!40000 ALTER TABLE `contest_user` ENABLE KEYS */;
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
  `create_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,35,'我的小组1','123',1514374777412),(2,35,'sergsreg',NULL,1514454779295),(4,35,'大红鹰','123',1517153734843),(5,35,'比赛小组测试',NULL,1517380177747);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `group_user`
--

LOCK TABLES `group_user` WRITE;
/*!40000 ALTER TABLE `group_user` DISABLE KEYS */;
INSERT INTO `group_user` VALUES (1,35,'我是Smith1',0,0,0,0,0,0,0,1517212521967),(2,35,'我是Smith1',0,0,0,0,0,0,0,1517201875418),(5,35,'我是Smith1',2,1,1,0,1,0,0,1517381027066);
/*!40000 ALTER TABLE `group_user` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `judger`
--

LOCK TABLES `judger` WRITE;
/*!40000 ALTER TABLE `judger` DISABLE KEYS */;
INSERT INTO `judger` VALUES (3,'101.132.164.120',5000,1517555815308);
/*!40000 ALTER TABLE `judger` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514278862084),(2,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514279642076),(3,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514279838486),(4,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514280061191),(5,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514280093555),(6,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514289676933),(7,35,0,'<a href=\"/#/contest/2\">红鹰杯2</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/2/leaderboard\"></a>','{}',1514289824868),(8,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\"></a>','{}',1514289978292),(9,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\"></a>','{}',1514289978352),(10,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514293165340),(11,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514293165395),(12,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514362414496),(13,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514362414554),(14,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363399813),(15,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363399858),(16,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363505878),(17,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363505931),(18,1,0,'href=\"/#/contest/5/leaderboard\">','{}',1514363505931),(19,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363620256),(20,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363620312),(21,0,1,'','{\"cid\": 5, \"name\": \"新的比赛\", \"rank\": [{\"cid\": 5, \"uid\": 35, \"score\": 10, \"ac_times\": 2, \"nickname\": \"我是Smith\", \"used_time\": 39764383, \"submit_times\": 10, \"finished_problems\": 2}, {\"cid\": 5, \"uid\": 37, \"score\": 0, \"ac_times\": 1, \"nickname\": \"大海\", \"used_time\": 0, \"submit_times\": 1, \"finished_problems\": 1}]}',1514363620344),(22,37,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第2名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363847188),(23,35,0,'<a href=\"/#/contest/5\">新的比赛</a>已经结束，你获得了第1名，查看<a href=\"/#/contest/5/leaderboard\">排行榜</a>','{}',1514363847251),(24,0,1,'','{\"cid\": 5, \"name\": \"新的比赛\", \"rank\": [{\"uid\": 35, \"nickname\": \"我是Smith\"}, {\"uid\": 37, \"nickname\": \"大海\"}]}',1514363847338),(25,35,0,'你被<a href=\"/#/group/1\">我的小组</a>小组拉入了<a href=\"/#/contest/1\">红鹰杯1</a>比赛','{}',1516010746839),(26,35,0,'你被<a href=\"/#/group/1\">我的小组</a>小组拉入了<a href=\"/#/contest/1\">红鹰杯1</a>比赛','{}',1516011040723),(27,35,0,'<a href=\"/#/group/1\">我的小组</a>小组发送通知：给你的组员发送消息给你的组员发送消息','{}',1516017395936),(28,35,0,'<a href=\"/#/group/1\">我的小组</a>小组发送通知：给你的组员发送消息给你的组员发送消息','{}',1516017420881),(29,35,0,'<a href=\"/#/group/1\">我的小组</a>小组发送通知：给你的组员发送消息给你的组员发送消息','{}',1516017462534),(30,35,0,'您的<a href=\"/#/problem/9\">langs12</a>已经审核通过','{}',1516785375859),(31,35,0,'您的<a href=\"/#/problem/9\">langs12</a>题目已经审核通过','{}',1516785500417),(32,35,0,'您的<a href=\"/#/problem/9\">langs12</a>题目审核不通过，请修改后再次审核','{}',1516785582828),(33,35,0,'<a href=\"/#/group/1\">我的小组1</a>小组发送通知：efaeeeeeeeeeeeeeeeeee','{}',1517022721534),(34,35,0,'<a href=\"/#/contest/8\">aef</a>比赛已经结束，你获得了第1名，查看<a href=\"/#/contest/8/leaderboard\">排行榜</a>','{}',1517193239898),(35,35,0,'<a href=\"/#/group/1\">我的小组1</a>小组发送通知：hello world!!!','{}',1517212538915),(36,35,0,'您的<a href=\"/#/problem/2\">FJ沙盘问题</a>题目已经审核通过','{}',1517473499942),(37,37,0,'<a href=\"/#/contest/12\">比赛小组</a>比赛已经结束，你获得了第1名，查看<a href=\"/#/contest/12/leaderboard\">排行榜</a>','{}',1517561396553),(38,35,0,'<a href=\"/#/contest/12\">比赛小组</a>比赛已经结束，你获得了第2名，查看<a href=\"/#/contest/12/leaderboard\">排行榜</a>','{}',1517561396598),(39,37,0,'<a href=\"/#/contest/11\">ACM比赛模式测试</a>比赛已经结束，你获得了第1名，查看<a href=\"/#/contest/11/leaderboard\">排行榜</a>','{}',1517561396770),(40,35,0,'<a href=\"/#/contest/11\">ACM比赛模式测试</a>比赛已经结束，你获得了第2名，查看<a href=\"/#/contest/11/leaderboard\">排行榜</a>','{}',1517561396838),(41,37,0,'<a href=\"/#/contest/10\">OI比赛模式测试</a>比赛已经结束，你获得了第1名，查看<a href=\"/#/contest/10/leaderboard\">排行榜</a>','{}',1517561396909),(42,35,0,'<a href=\"/#/contest/10\">OI比赛模式测试</a>比赛已经结束，你获得了第2名，查看<a href=\"/#/contest/10/leaderboard\">排行榜</a>','{}',1517561396959),(43,35,0,'<a href=\"/#/group/1\">我的小组1</a>小组发送通知：eafaefaefaefaefaef','{}',1517568537974);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (1,35,'2n皇后问题','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"给定一个n*n的棋盘，棋盘中有一些位置不能放皇后。现在要向棋盘中放入n个黑皇后和n个白皇后，使任意的两个黑皇后都不在同一行、同一列或同一条对角线上，任意的两个白皇后都不在同一行、同一列或同一条对角线上。问总共有多少种放法？n小于等于8。\\n\"}]}','{\"ops\": [{\"insert\": \"输入的第一行为一个整数n，表示棋盘的大小。接下来n行，每行n个0或1的整数，如果一个整数为1，表示对应的位置可以放皇后，如果一个整数为0，表示对应的位置不可以放皇后。\\n\"}]}','{\"ops\": [{\"insert\": \"输出一个整数，表示总共有多少种放法。\\n\"}]}',1,'[{\"input\": \"4\\n1 1 1 1\\n1 1 1 1\\n1 1 1 1\\n1 1 1 1\", \"output\": \"2\"}, {\"input\": \"4\\n1 0 1 1\\n1 1 1 1\\n1 1 1 1\\n1 1 1 1\", \"output\": \"0\"}]',3,128,30,2,5,3,14,3,7,2,1512628342544),(2,35,'FJ沙盘问题','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"FJ在沙盘上写了这样一些字符串：\\n  A1 = “A”\\n  A2 = “ABA”\\n  A3 = “ABACABA”\\n  A4 = “ABACABADABACABA”\\n  … …\\n你能找出其中的规律并写所有的数列AN吗？\\n\"}]}','{\"ops\": [{\"insert\": \"仅有一个数：N ≤ 26。\\n\"}]}','{\"ops\": [{\"insert\": \"请输出相应的字符串AN，以一个换行符结束。输出中不得含有多余的空格或换行、回车符。\\n\"}]}',1,'[{\"input\": \"a\", \"output\": \"ABACABA\"}]',3,128,8,3,1,2,0,0,5,2,1512636034347),(3,35,'Huffuman树','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"Huffman树在编码中有着广泛的应用。在这里，我们只关心Huffman树的构造过程。\\n  给出一列数{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}={\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"0\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \", \"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"1\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \", …, \"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"n\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"-1\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \"}，用这列数构造Huffman树的过程如下：\\n  1. 找到{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中最小的两个数，设为\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"a\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"和\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"b\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"，将\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"a\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"和\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"b\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"从{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中删除掉，然后将它们的和加入到{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中。这个过程的费用记为\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"a\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \" + \"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"b\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"。\\n  2. 重复步骤1，直到{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中只剩下一个数。\\n  在上面的操作过程中，把所有的费用相加，就得到了构造Huffman树的总费用。\\n  本题任务：对于给定的一个数列，现在请你求出用该数列构造Huffman树的总费用。\\n \\n  例如，对于数列{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}={5, 3, 8, 2, 9}，Huffman树的构造过程如下：\\n  1. 找到{5, 3, 8, 2, 9}中最小的两个数，分别是2和3，从{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中删除它们并将和5加入，得到{5, 8, 9, 5}，费用为5。\\n  2. 找到{5, 8, 9, 5}中最小的两个数，分别是5和5，从{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中删除它们并将和10加入，得到{8, 9, 10}，费用为10。\\n  3. 找到{8, 9, 10}中最小的两个数，分别是8和9，从{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中删除它们并将和17加入，得到{10, 17}，费用为17。\\n  4. 找到{10, 17}中最小的两个数，分别是10和17，从{\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"i\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"}中删除它们并将和27加入，得到{27}，费用为27。\\n  5. 现在，数列中只剩下一个数27，构造过程结束，总费用为5+10+17+27=59。\\n\"}]}','{\"ops\": [{\"insert\": \"输入的第一行包含一个正整数\"}, {\"insert\": \"n\", \"attributes\": {\"italic\": true}}, {\"insert\": \"（\"}, {\"insert\": \"n\", \"attributes\": {\"italic\": true}}, {\"insert\": \"<=100）。\\n接下来是\"}, {\"insert\": \"n\", \"attributes\": {\"italic\": true}}, {\"insert\": \"个正整数，表示\"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"0\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \", \"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"1\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \", …, \"}, {\"insert\": \"p\", \"attributes\": {\"italic\": true}}, {\"insert\": \"n\", \"attributes\": {\"italic\": true, \"script\": \"sub\"}}, {\"insert\": \"-1\", \"attributes\": {\"script\": \"sub\"}}, {\"insert\": \"，每个数不超过1000。\\n\"}]}','{\"ops\": [{\"insert\": \"输出用这些数构造Huffman树的总费用。\\n\"}]}',2,'[{\"input\": \"5\\n5 3 8 2 9\", \"output\": \"59\"}]',3,128,3,0,0,1,1,0,1,2,1512636389524),(4,35,'HelloWorld测试','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"HelloWorld测试\\n\"}]}','{\"ops\": [{\"insert\": \"HelloWorld测试\\n\"}]}','{\"ops\": [{\"insert\": \"HelloWorld测试\\n\"}]}',0,'[{\"input\": \"none\", \"output\": \"hello world\"}]',3,128,1,1,1,0,0,0,0,2,1512981640639),(5,35,'A+B problem','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"A+B problem\\n\"}]}','{\"ops\": [{\"insert\": \"A+B problem\\n\"}]}','{\"ops\": [{\"insert\": \"A+B problem\\n\"}]}',0,'[{\"input\": \"none\", \"output\": \"A+B\"}]',3,128,17,0,1,1,0,0,0,2,1512981691684),(6,35,'输出大海','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"输出大海\\n\"}]}','{\"ops\": [{\"insert\": \"输出大海\\n\"}]}','{\"ops\": [{\"insert\": \"输出大海\\n\"}]}',0,'[{\"input\": \"输出大海\", \"output\": \"输出大海\"}]',3,128,1,0,0,0,1,0,0,2,1512981720364),(7,35,'train question','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"train question\\n\"}, {\"insert\": {\"formula\": \"\\\\sum_{i=0}^{100}x_i\"}}, {\"insert\": \" \\n\"}]}','{\"ops\": [{\"insert\": \"train question\\n\"}]}','{\"ops\": [{\"insert\": \"train question\\n\"}]}',0,'[{\"input\": \"train\", \"output\": \"train\"}]',3,128,3,0,0,0,0,0,3,2,1512981752502),(8,35,'memory and time test','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"memory and time test\\n\"}]}','{\"ops\": [{\"insert\": \"memory and time test\\n\"}]}','{\"ops\": [{\"insert\": \"memory and time test\\n\"}]}',0,'[{\"input\": \"memory and time test11\", \"output\": \"memory and time test\"}]',3,128,0,0,0,0,0,0,0,0,1513428646763),(9,35,'langs12ae','[\"JAVA8\", \"PYTHON35\", \"CPP\", \"C\", \"PYTHON27\"]','{\"ops\": [{\"insert\": \"langs\\n\"}]}','{\"ops\": [{\"insert\": \"langs\\n\"}]}','{\"ops\": [{\"insert\": \"langs\\n\"}]}',3,'[{\"input\": \"langs\", \"output\": \"langs\"}]',4,129,0,0,0,0,0,0,0,0,1513432879839),(10,35,'test','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"aewtast\\n\"}]}','{\"ops\": [{\"insert\": \"awetast\\n\"}]}','{\"ops\": [{\"insert\": \"awefasfa\\n\"}]}',0,'[{\"input\": \"123\", \"output\": \"123\"}]',3,128,0,0,0,0,0,0,0,2,1514725698114),(11,37,'大海123','[\"JAVA8\", \"PYTHON27\", \"CPP\", \"C\", \"PYTHON35\"]','{\"ops\": [{\"insert\": \"大海大海大海大海\\n\"}]}','{\"ops\": [{\"insert\": \"大海大海大海123\\n\"}]}','{\"ops\": [{\"insert\": \"大海大海大海大海大海大海\\n\"}]}',3,'[{\"input\": \"阿尔法额算法\", \"output\": \"阿尔法色\"}, {\"input\": \"在线\", \"output\": \"中心小学\"}]',5,128,0,1,0,0,0,0,0,2,1517060955958),(12,37,'aef','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"aefasfe\\n\"}]}','{\"ops\": [{\"insert\": \"aefe\\n\"}]}','{\"ops\": [{\"insert\": \"ef\\n\"}]}',0,'[{\"input\": \"aef\", \"output\": \"aef\"}, {\"input\": \"\", \"output\": \"\"}]',3,128,0,0,0,0,0,0,0,0,1517065450863),(13,35,'比赛模式测试题目','[\"JAVA8\", \"PYTHON27\", \"PYTHON35\", \"CPP\", \"C\"]','{\"ops\": [{\"insert\": \"比赛模式测试题目比赛模式测试题目比赛模式测试题目1\\n\"}]}','{\"ops\": [{\"insert\": \"比赛模式测试题目比赛模式测试题目\\n\"}]}','{\"ops\": [{\"insert\": \"比赛模式测试题目\\n\"}]}',0,'[{\"input\": \"比赛模式测试题目\", \"output\": \"比赛模式测试题目\"}]',3,128,15,2,6,4,1,0,4,0,1517369678104);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `problem_moderator`
--

LOCK TABLES `problem_moderator` WRITE;
/*!40000 ALTER TABLE `problem_moderator` DISABLE KEYS */;
INSERT INTO `problem_moderator` VALUES (9,1),(10,1),(10,37),(12,1),(12,35);
/*!40000 ALTER TABLE `problem_moderator` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `problem_user`
--

LOCK TABLES `problem_user` WRITE;
/*!40000 ALTER TABLE `problem_user` DISABLE KEYS */;
INSERT INTO `problem_user` VALUES (1,35,'RTE'),(2,35,'CE'),(3,35,'CE'),(5,35,'AC'),(6,35,'RTE'),(7,35,'CE');
/*!40000 ALTER TABLE `problem_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `key` varchar(20) NOT NULL,
  `value` varchar(1000) NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('oss_access_key','LTAIbIlfxxtKu7an'),('oss_bucket','eagle-oj'),('oss_end_point','oss-cn-shanghai.aliyuncs.com'),('oss_secret_key','v2l8CRlTPtLvz6ocp2BvFWedxfoSdo'),('oss_url','http://eagle-oj.oss-cn-shanghai.aliyuncs.com'),('web_title','yyyyyyyy');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission`
--

LOCK TABLES `submission` WRITE;
/*!40000 ALTER TABLE `submission` DISABLE KEYS */;
INSERT INTO `submission` VALUES (1,35,1,0,0,34,'PYTHON35',0.02,4,'WA',1512889563768),(2,35,1,0,0,35,'PYTHON35',0.02,4,'WA',1512889640777),(3,35,1,0,0,36,'PYTHON35',0.02,4,'WA',1512889838073),(4,35,1,0,0,37,'PYTHON35',0.02,4,'WA',1512890081358),(5,35,1,0,0,38,'PYTHON35',0.02,4,'WA',1512890093482),(6,35,1,0,0,39,'PYTHON35',0.02,4,'RTE',1512890122751),(7,35,1,0,0,40,'PYTHON35',0.02,4,'RTE',1512893628870),(8,35,1,0,0,41,'PYTHON35',0.02,4,'RTE',1512893939288),(9,35,1,0,0,42,'PYTHON35',0.02,4,'RTE',1512893981722),(10,35,1,0,0,44,'PYTHON35',0.02,4,'RTE',1512893981769),(11,35,1,0,0,43,'PYTHON35',0.02,4,'RTE',1512893981803),(12,35,1,0,0,45,'PYTHON35',0.02,4,'RTE',1512893982621),(13,35,1,0,0,46,'PYTHON35',0.02,4,'RTE',1512893993134),(14,35,1,0,0,47,'PYTHON35',0.02,4,'RTE',1512893993836),(15,35,1,0,0,48,'PYTHON35',0.02,4,'RTE',1512893994508),(16,35,1,0,0,49,'PYTHON35',0.02,4,'RTE',1512894300186),(17,35,1,2,0,50,'PYTHON35',0.02,4,'RTE',1512962187357),(18,35,1,2,0,51,'PYTHON35',0.02,4,'RTE',1512962310382),(19,35,1,2,0,52,'PYTHON35',0.03,4,'RTE',1512963061733),(20,35,1,2,0,53,'PYTHON35',0.02,4,'RTE',1512963126252),(21,35,1,2,0,54,'PYTHON35',0.02,4,'RTE',1512963226327),(22,35,1,2,0,55,'PYTHON35',3.00,4,'TLE',1512963283161),(23,35,1,2,0,56,'PYTHON35',3.00,4,'TLE',1513158419662),(24,35,1,2,0,57,'PYTHON35',3.00,4,'TLE',1513158780936),(25,35,1,0,0,58,'C',0.01,1,'AC',1513305875129),(26,35,7,0,0,59,'JAVA8',0.00,0,'CE',1513436332431),(27,35,7,0,0,60,'JAVA8',0.00,0,'CE',1513436345803),(28,35,7,0,0,61,'JAVA8',0.00,0,'CE',1513436436244),(29,35,1,0,0,62,'C',0.01,1,'AC',1513436805049),(30,35,1,0,0,63,'JAVA8',0.00,0,'CE',1513565329165),(31,35,1,0,0,64,'JAVA8',0.00,0,'CE',1513565426971),(32,35,1,0,0,65,'JAVA8',0.08,8,'WA',1513565468684),(33,35,1,0,0,66,'C',0.01,1,'AC',1513565493774),(34,35,1,0,0,67,'PYTHON35',0.02,4,'WA',1513566049401),(35,35,1,0,0,68,'PYTHON35',0.02,4,'WA',1513566157792),(36,35,2,0,0,69,'PYTHON35',0.02,4,'WA',1513566193981),(37,35,6,0,0,70,'PYTHON35',0.03,5,'RTE',1513572068453),(38,35,1,5,0,71,'JAVA8',0.00,0,'CE',1513588807600),(39,35,1,5,0,72,'JAVA8',0.00,0,'CE',1513589003093),(40,35,1,5,0,73,'JAVA8',0.00,0,'CE',1513589055936),(41,35,1,5,0,74,'JAVA8',0.00,0,'CE',1513589145985),(42,35,1,5,0,75,'JAVA8',0.00,0,'CE',1513589198388),(43,35,1,5,0,76,'PYTHON35',0.02,4,'WA',1513590336451),(44,35,3,5,0,77,'PYTHON35',0.02,4,'WA',1513590855653),(45,37,1,5,0,78,'C',0.01,1,'AC',1513594239002),(46,35,2,5,0,79,'C',0.00,0,'WA',1513860193521),(47,35,2,5,0,80,'PYTHON35',0.02,4,'AC',1513860339292),(48,35,1,5,0,81,'C',0.01,1,'AC',1513911476041),(49,35,1,5,0,82,'C',0.01,1,'AC',1513911764259),(50,35,5,0,0,83,'CPP',0.00,0,'WA',1514445735420),(51,35,5,0,0,84,'CPP',0.00,0,'AC',1514445772436),(52,35,3,0,0,88,'JAVA8',0.00,0,'CE',1517023032499),(53,35,3,0,0,89,'JAVA8',0.00,0,'CE',1517023084391),(54,35,3,0,0,90,'JAVA8',0.00,0,'CE',1517023240623),(55,35,3,5,0,91,'PYTHON27',0.01,7,'RTE',1517023369295),(56,35,3,5,0,92,'PYTHON27',0.01,4,'RTE',1517023455844),(57,35,3,5,0,93,'PYTHON27',0.01,3,'RTE',1517023554069),(58,35,1,8,0,97,'PYTHON27',0.01,4,'RTE',1517146525101),(60,35,2,0,0,99,'JAVA8',0.01,0,'CE',1517322581252),(62,35,2,0,0,101,'JAVA8',0.01,0,'CE',1517324029284),(66,35,2,0,0,105,'JAVA8',0.01,0,'CE',1517326544763),(67,35,2,0,0,106,'JAVA8',0.01,0,'CE',1517326573267),(68,35,1,0,0,107,'JAVA8',0.01,0,'CE',1517326795359),(69,35,1,0,0,108,'JAVA8',0.01,0,'CE',1517326843839),(70,35,1,0,0,109,'PYTHON35',0.03,6,'WA',1517326869786),(71,35,1,0,0,110,'PYTHON35',0.03,6,'WA',1517326893222),(72,35,13,10,0,111,'PYTHON35',0.03,6,'WA',1517370258380),(73,35,13,10,0,112,'PYTHON35',0.03,6,'AC',1517370315455),(74,35,13,10,0,113,'PYTHON35',0.03,6,'AC',1517373366898),(75,35,13,10,0,114,'PYTHON35',0.03,6,'AC',1517373390547),(76,35,13,11,0,115,'PYTHON35',0.03,6,'WA',1517379507789),(77,35,13,11,0,116,'PYTHON35',0.03,6,'WA',1517379554250),(78,35,13,11,0,117,'PYTHON35',0.03,6,'AC',1517379568606),(79,35,13,11,0,118,'PYTHON35',0.03,6,'AC',1517379843870),(80,35,13,12,0,119,'JAVA8',0.01,0,'CE',1517381043152),(81,35,13,12,0,120,'JAVA8',0.01,0,'CE',1517381122672),(82,35,13,12,0,121,'JAVA8',0.01,0,'CE',1517381398507),(83,35,13,12,0,122,'JAVA8',0.01,0,'CE',1517381421090),(84,35,13,12,5,123,'PYTHON27',0.01,0,'RTE',1517382080859),(85,35,13,12,5,124,'PYTHON35',0.03,6,'AC',1517382272310),(86,37,13,10,0,125,'PYTHON35',0.03,6,'WA',1517383373253),(87,37,2,10,0,126,'C',0.01,0,'CE',1517383529632),(88,37,4,10,0,127,'PYTHON35',0.03,6,'AC',1517383545100),(89,37,13,11,0,128,'PYTHON35',0.03,6,'AC',1517396754614),(90,37,1,11,0,129,'JAVA8',0.01,0,'CE',1517397232445),(91,35,1,0,0,130,'PYTHON27',0.01,0,'RTE',1517397832670);
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
INSERT INTO `tag_problem` VALUES (1,2),(1,4),(1,6),(1,7),(1,9),(1,10),(1,11),(1,13),(2,4),(2,5),(2,7),(2,8),(2,9),(2,10),(2,12),(3,4),(3,5),(3,7),(4,4),(4,5),(5,1),(5,4),(5,8),(6,3),(6,11);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'java1',15),(2,'python',15),(3,'hellworld',11),(4,'链表',7),(5,'DFS',6),(6,'哈夫曼',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_cases`
--

LOCK TABLES `test_cases` WRITE;
/*!40000 ALTER TABLE `test_cases` DISABLE KEYS */;
INSERT INTO `test_cases` VALUES (1,1,'3\n1 1 0 \n1 1 1 \n1 1 0 ','0',1,1512631461719),(2,1,'4\n1 1 1 1 \n1 0 1 1 \n1 1 1 1 \n1 1 1 1','2',1,1512632319498),(3,1,'5\n1 1 1 1 1 \n1 0 1 1 1 \n1 1 1 1 1 \n1 0 1 1 1 \n1 1 1 1 1','12',1,1512632336990),(4,1,'6\n1 1 1 1 1 1 \n1 1 1 1 1 1 \n1 1 1 1 1 1 \n1 1 1 1 1 1 \n1 1 1 1 1 1 \n1 1 1 1 1 1','12',1,1512632350016),(5,1,'7\n1 1 1 1 1 1 0 \n1 1 1 1 1 1 1 \n1 1 1 1 1 1 1 \n1 1 1 1 1 1 1 \n1 1 1 1 1 1 1 \n1 1 1 1 1 1 1 \n1 1 1 1 1 1 1','408',1,1512632362740),(6,2,'10','hello',1,1512636173870),(7,2,'9','hello',3,1512636201839),(8,3,'2\n42 468','510',1,1512636419045),(9,3,'5\n335 501 170 725 479','4925',1,1512636438162),(10,4,'','hello world',1,1512981648519),(11,5,'','A+B',1,1512981699333),(12,6,'输出大海','输出大海',1,1512981724423),(13,7,'train','train',1,1512981757985),(14,7,'','train',3,1512981763614),(16,10,'1231','123rg1',4,1514729936509),(18,9,'test','teststsete',1,1517040587691),(19,12,'eaF','aefef',2,1517065925684),(21,13,'1','1',1,1517369689046),(22,13,'2','2',2,1517369693506),(23,13,'5','5',5,1517369699631);
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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@admin.com','admin',132,'21232f297a57a5a743894a0e4a801fc3',9,'[]',0,0,0,0,0,0,0,0,0,NULL,1517540011723,0),(3,'chen!@126.com','smith',0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509625563796,0),(5,'chen!1@126.com','smith',0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509625680241,0),(6,'chen!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509625693663,0),(8,'che1n!11@126.com','smith',0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509625797534,0),(9,'c1@126.com1','test',0,'123456',0,'[]',0,0,0,0,0,0,0,0,0,NULL,0,0),(10,'c1@126.com','test',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,0,0),(11,'test@test.com2','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,NULL,0,0),(12,'test@test.com1','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509886602210,0),(13,'test@test.com3','I Am Test',0,'098f6bcd4621d373cade4e832627b4f6',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1509964177613,0),(14,'test@test.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',14,0,2,0,0,0,0,0,0,NULL,1510129937075,0),(15,'1510157247573@126.com','1510157247573-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510157248389,0),(16,'1510157414297@126.com','1510157414297-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510157414311,0),(17,'1510200482152@126.com','1510200482152-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510200482167,0),(18,'1510200639157@126.com','1510200639157-TESTER',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510200639171,0),(19,'test@test1.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510377927655,0),(21,'test@test2.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510377955548,0),(23,'test@test99.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510377967933,0),(25,'test@test9.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510378047399,0),(27,'test@test8.com','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510378179880,0),(28,'test@test.comx','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510462603114,0),(29,'test@test.comxb','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510462649043,0),(30,'test@test.com7','I am tester',0,'e10adc3949ba59abbe56e057f20f883e',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1510464192666,0),(31,'fe@12.dth','asef',0,'d9b1d7db4cd6e70935368a1efb10e377',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511771980307,0),(32,'chen1@126.com','abc',0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511772230886,0),(33,'chen@126.com','abc',0,'202cb962ac59075b964b07152d234b70',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511772355487,0),(34,'1@126.com','aef',0,'202cb962ac59075b964b07152d234b70',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511772618636,0),(35,'1@1.com','我是Smith1',131,'81dc9bdb52d04dc20036dbd8313ed055',9,'[]',23,12,2,4,13,0,5,2,0,'我是孤独求败的人1',1511772653407,0),(36,'12@1.com','aec',0,'202cb962ac59075b964b07152d234b70',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511772836330,0),(37,'2@2.com','大海',0,'202cb962ac59075b964b07152d234b70',8,'[]',0,2,0,0,0,0,0,0,0,NULL,1511772924348,0),(38,'danny@126.com','danny',0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1511774201713,0),(39,'dfgv@12.com','aewfeasf',0,'14e1b600b1fd579f47433b88e8d85291',0,'[]',0,0,0,0,0,0,0,0,0,NULL,1512306366983,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `user_log`
--

LOCK TABLES `user_log` WRITE;
/*!40000 ALTER TABLE `user_log` DISABLE KEYS */;
INSERT INTO `user_log` VALUES ('2017-12-18',35,28,12,6,1,0,6),('2017-12-18',37,12,6,0,0,0,0),('2017-12-21',35,2,1,1,0,0,0),('2017-12-22',35,2,2,0,0,0,0),('2017-12-28',35,2,1,1,0,0,0),('2018-01-27',35,4,3,3,3,3,4),('2018-01-28',34,0,0,0,0,0,0),('2018-01-28',35,1,0,0,1,0,0),('2018-01-30',35,7,0,2,0,0,5),('2018-01-31',35,15,6,3,2,0,4),('2018-01-31',37,5,2,1,0,0,2);
/*!40000 ALTER TABLE `user_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-03 16:26:23
