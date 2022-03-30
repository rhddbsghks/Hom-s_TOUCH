package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.HouseDealDto;
import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HappyHouseMapService;

@RestController
@RequestMapping("/map")
public class HouseMapController {
	
	@Autowired
	private HappyHouseMapService happyHouseMapService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception{
		return new ResponseEntity<List<SidoGugunCodeDto>>(happyHouseMapService.getSido(),HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception{
		return new ResponseEntity<List<SidoGugunCodeDto>>(happyHouseMapService.getGugunInSido(sido),HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception{
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getDongInGugun(gugun),HttpStatus.OK);
	}
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception{
		return new ResponseEntity<List<HouseInfoDto>>(happyHouseMapService.getAptInDong(dong),HttpStatus.OK);
	}
	@GetMapping("/apt_detail")
	public ResponseEntity<HouseInfoDto> apt_detail(@RequestParam("aptCode") String aptCode) throws Exception{
		return new ResponseEntity<HouseInfoDto>(happyHouseMapService.getAptDetail(aptCode),HttpStatus.OK);
	}
	
	@GetMapping("/apt_deal")
	public ResponseEntity<List<HouseDealDto>> apt_deal(@RequestParam("aptCode") String aptCode) throws Exception{
		return new ResponseEntity<List<HouseDealDto>>(happyHouseMapService.getAptDeal(aptCode),HttpStatus.OK);
	}

}