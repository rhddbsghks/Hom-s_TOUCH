package com.ssafy.happyhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ssafy.happyhouse.model.mapper")
public class HappyHouseBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouseBackEndApplication.class, args);
	}

}
