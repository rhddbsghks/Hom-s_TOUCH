CREATE TABLE `interest_dong` (
  `id` varchar(15) NOT NULL,
  `dongCode` varchar(10) NOT NULL,
  PRIMARY KEY (`id`,`dongCode`),
  CONSTRAINT `interest_dong_id_FK` FOREIGN KEY (`id`) REFERENCES `userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;