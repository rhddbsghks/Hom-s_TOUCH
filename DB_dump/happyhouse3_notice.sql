-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: happyhouse3
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_no` int NOT NULL AUTO_INCREMENT,
  `id` varchar(45) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `content` text,
  `regtime` varchar(45) DEFAULT NULL,
  `view` int DEFAULT '0',
  PRIMARY KEY (`notice_no`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'admin','공지1','test111','2021-11-07 20:11:21',26),(2,'ssafy','ss테스트','ssafy 6기 화이팅','2021-11-07 20:32:58',22),(3,'ssafy','ㅇㅋ','ssafy 6기 화이팅','2021-11-22 20:32:58',103),(18,'ssafy','ㅇㅈㅇㅈㅇㅈ','ㅇㅈ','2021-11-22 16:47:26',23),(32,'ssafy','ㄹㅇㅋㅋ','dd','2021-11-23 01:32:13',3),(33,'ssafy','gd','gd','2021-11-23 01:33:56',1),(34,'ssafy','ddd','dd','2021-11-23 01:33:59',4),(35,'ssafy','dddd','dd','2021-11-23 01:34:01',9),(36,'ssafy','ddd','ddd','2021-11-23 01:34:05',8),(37,'ssafy','asdsdfa','sdfasdf','2021-11-23 01:34:12',9),(38,'ssafy','asdfa','sdfasdfasdf','2021-11-23 01:34:19',2),(39,'ssafy','ㄴ페이징테스트용','ㄴ','2021-11-23 02:41:48',0),(40,'ssafy','ㄴ페이징테스트용','ㄴ','2021-11-23 02:41:50',0),(41,'ssafy','ㄴ','ㄴ','2021-11-23 02:41:53',0),(42,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:41:57',0),(43,'ssafy','ㄴ','ㄴ','2021-11-23 02:41:59',0),(44,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:42:04',0),(45,'ssafy','ㄴ페이징테스트용','ㄴ','2021-11-23 02:42:06',0),(46,'ssafy','ㄴ페이징테스트용','ㄴ','2021-11-23 02:42:10',0),(47,'ssafy','ㄴ페이징테스트용','ㄴ','2021-11-23 02:42:12',0),(48,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:42:14',0),(49,'ssafy','페이징테스트용',NULL,'2021-11-23 02:42:16',3),(50,'ssafy','페이징테스트용',NULL,'2021-11-23 02:42:20',2),(51,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:42:22',0),(53,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:42:26',1),(55,'ssafy','페이징테스트용페이징테스트용ㄴ','ㄴ','2021-11-23 02:42:31',1),(56,'ssafy','페이징테스트용','ㄴ','2021-11-23 02:42:32',0),(62,'ssafy','ss테스트','ssafy 6기 화이팅','2021-11-07 20:32:58',23),(63,'ssafy','ㅇㅋ','ssafy 6기 화이팅','2021-11-22 20:32:58',103),(64,'ssafy','ㅇㅈㅇㅈㅇㅈ','ㅇㅈ','2021-11-22 16:47:26',23),(65,'ssafy','ㄹㅇㅋㅋ','dd','2021-11-23 01:32:13',6),(66,'ssafy','gd','gd','2021-11-23 01:33:56',2),(67,'ssafy','ddd','dd','2021-11-23 01:33:59',13),(68,'ssafy','페이징테스트용','dd','2021-11-23 01:34:01',23),(69,'ssafy','ddd','ddd','2021-11-23 01:34:05',25),(70,'admin','페이징테스트용','gg','2021-11-23 02:52:42',4),(71,'admin','gg','gg','2021-11-23 02:52:45',3),(72,'ssafy','wow','와웅','2021-11-23 03:04:44',141),(73,'admin','123','ddd','2021-11-23 10:49:26',8),(74,'11111','서울 6반 화이팅','화이팅!','2021-11-24 09:32:33',12),(75,'admin','사이트 점검 예정입니다.','해당 시간 서비스를 이용할 수 없습니다.','2021-11-24 09:33:36',5),(76,'gong','서울 6반 포에버','화이팅!','2021-11-25 02:47:46',23),(77,'doraemong','[급처] 대나무 헬리콥터 급처합니다!!!','네이버 최저가 6만원인데 \n2만원에 떨이합니다.','2021-11-25 02:50:23',29),(78,'admin','11/26 종강식 예정입니다.','많은 참여 부탁드립니다.','2021-11-25 09:44:10',13),(79,'11111','1학기 끝 실환가...','시간 너무 빠르네요 ㅠㅠ\n남은 기간도 화이팅...','2021-11-25 21:59:16',18),(80,'kimssafy','7기 김싸피 가입인사드립니다. ','잘 부탁드립니다.','2021-11-25 22:21:39',13),(82,'kdk','저 오늘 발표 다 끝나면...','웹엑스 라이브 댄스공연 시작합니다.\n말리지 마세요?','2021-11-26 03:30:31',14);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-26 14:21:52
