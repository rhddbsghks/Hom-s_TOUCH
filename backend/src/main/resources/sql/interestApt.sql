CREATE TABLE `interest_apt` (
  `id` varchar(15) NOT NULL,
  `aptCode` int NOT NULL,
  PRIMARY KEY (`id`,`aptCode`),
  CONSTRAINT `interest_apt_id_FK` FOREIGN KEY (`id`) REFERENCES `userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;