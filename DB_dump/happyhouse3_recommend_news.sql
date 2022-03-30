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
-- Table structure for table `recommend_news`
--

DROP TABLE IF EXISTS `recommend_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recommend_news` (
  `no` int NOT NULL AUTO_INCREMENT,
  `link` text NOT NULL,
  `thumbnail` text NOT NULL,
  `title` text NOT NULL,
  `regtime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommend_news`
--

LOCK TABLES `recommend_news` WRITE;
/*!40000 ALTER TABLE `recommend_news` DISABLE KEYS */;
INSERT INTO `recommend_news` VALUES (17,'https://www.news1.kr/articles/?4473492','https://image.news1.kr/system/photos/2021/10/27/5037007/article.jpg','\"미래 개발자 꿈 응원한다\"…\'SSAFY 밋업\' 온라인 개최','2021-11-25 00:38:08'),(18,'https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=101&oid=031&aid=0000638423','https://imgnews.pstatic.net/image/031/2021/11/24/0000638423_001_20211124162801187.jpg','\'뉴 삼성\' 고삐 죄는 이재용…美 \'인싸\' 만나 미래 그렸다','2021-11-25 01:19:54'),(19,'https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=101&oid=018&aid=0005093389','https://imgnews.pstatic.net/image/018/2021/11/24/0005093389_001_20211124171301804.jpg','SKC, 산업은행서 1.5조 조달…\'배터리·소재\' 육성 자금','2021-11-25 01:20:01'),(20,'https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=101&oid=003&aid=0010852606','https://imgnews.pstatic.net/image/003/2021/11/24/NISI20211124_0018186863_web_20211124091345_20211124235005096.jpg','10년만 전략비축유 방출…정유업계 \"공급부족 시그널, 장기적 유가 상승\"','2021-11-25 01:20:05'),(23,'https://newsis.com/view/?id=NISX20211125_0001664268&cID=13001&pID=13000','http://image.newsis.com/2021/11/25/NISI20211125_0000877660_web.jpg','카카오 지갑에 \'디지털 사원증\' 들어간다…서비스 확대','2021-11-25 22:33:44'),(27,'https://www.sedaily.com/NewsVIew/22U4N8OJ6B','https://newsimg.sedaily.com/2021/11/23/22U4N8OJ6B_1.jpg','꽉 막혔던 대출 숨통...국민銀, 잔금·전세대출 규제 완화','2021-11-26 01:22:17'),(28,'https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=100&oid=008&aid=0004675351','https://imgnews.pstatic.net/image/008/2021/11/25/0004675351_001_20211125160802429.jpg','피로감만 안긴 윤석열·김종인 기싸움··· 컨벤션 효과 사라졌다','2021-11-26 01:52:18'),(31,'https://the300.mt.co.kr/newsView.html?no=2020112911167690560&pDepth1=special_sub16&MC1','https://menu.mt.co.kr/the300/thumb/2020/11/04/2020112911167690560_1.jpg','\'새 대한민국\' 머리 맞댄 여야…\'공정성 회복·양극화 해소\' 화두 - the300','2021-11-26 04:18:53'),(32,'https://the300.mt.co.kr/newsView.html?no=2021031110357639348&pDepth1=special_sub17&MC3','https://menu.mt.co.kr/the300/thumb/2021/03/04/2021031110357639348_1.jpg','[단독]\"농어민도 가구당 100만원\"…與, \'4차 지원금\' 1.3조 증액 요구 - the300','2021-11-26 04:18:56'),(33,'https://www.chosun.com/economy/industry-company/2021/08/25/OZ5Z7PIFDFDCXMEHBUFY55X7RE/','https://images.chosun.com/resizer/QCoH7mD88gjY798QN8NxiUzmbt0=/1200x630/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/XBHPCXTWXBCYHKV4TVYAANDN5Q.jpg','좁은 취업문 뚫는 ‘삼성 SW아카데미’ 전국으로 확대','2021-11-26 04:19:26');
/*!40000 ALTER TABLE `recommend_news` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-26 14:21:50
