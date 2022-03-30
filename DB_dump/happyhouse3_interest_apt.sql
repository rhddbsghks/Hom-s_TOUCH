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
-- Table structure for table `interest_apt`
--

DROP TABLE IF EXISTS `interest_apt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest_apt` (
  `id` varchar(15) NOT NULL,
  `aptCode` varchar(20) NOT NULL,
  `aptName` varchar(45) DEFAULT NULL,
  `aptNickName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`aptCode`),
  CONSTRAINT `interest_apt_id_FK` FOREIGN KEY (`id`) REFERENCES `userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest_apt`
--

LOCK TABLES `interest_apt` WRITE;
/*!40000 ALTER TABLE `interest_apt` DISABLE KEYS */;
INSERT INTO `interest_apt` VALUES ('admin','11680-280','성원대치2단지아파트','성원대치2단지아파트'),('gong','11200-2923','갤러리아포레','죽기 전에 꼭 간다'),('gong','11260-81','다우훼밀리','간다ㅏ라'),('gong','11260-82','동광오뜨빌','동광스'),('gong','11260-86','라이프미성','미성미성'),('gong','11305-43','대우','대우대우짱'),('gong','11305-45','성원그린(103동)','성원아파트'),('gong','11305-70','두온리치벨리','두온'),('gong','11305-71','북한산노블리스빌','북한산노블리스빌'),('gong','11680-267','역삼동하나빌','역삼동하나빌'),('gong','11680-280','성원대치2단지아파트','성원대치2단지아파트'),('gong','11680-284','개포6차우성아파트1동~8동','개포6차우성아파트1동~8동'),('gong','11680-505','개포자이(12-2)','개포자이(12-2)'),('gong','11680-578','개포주공7단지','개포주공7단지'),('kimssafy','11680-204','국제','국제'),('kimssafy','11680-218','은마','존버아파트');
/*!40000 ALTER TABLE `interest_apt` ENABLE KEYS */;
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
