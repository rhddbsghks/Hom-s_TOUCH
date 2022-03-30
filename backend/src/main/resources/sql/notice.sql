CREATE TABLE `notice` (
  `notice_no` int NOT NULL AUTO_INCREMENT,
  `id` varchar(45) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `content` text,
  `regtime` varchar(45) DEFAULT NULL,
  `view` int DEFAULT '0',
  PRIMARY KEY (`notice_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;