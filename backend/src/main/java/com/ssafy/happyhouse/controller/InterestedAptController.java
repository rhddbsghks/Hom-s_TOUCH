package com.ssafy.happyhouse.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.InterestedApt;
import com.ssafy.happyhouse.model.service.InterestedAptService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/interesteApt")
public class InterestedAptController {

	@Autowired
	private InterestedAptService interestedAptService;


	@GetMapping("/list")
	public ResponseEntity<List<InterestedApt>> getList( String id) throws Exception {
		return new ResponseEntity<List<InterestedApt>> (interestedAptService.getInterestedApt(id),HttpStatus.OK);
	}


	@PostMapping("/insert")
	public String register(@RequestBody InterestedApt interestedApt) throws Exception {
		System.out.println(interestedApt);
		interestedAptService.insertInterestedApt(interestedApt);
		return "success";
	}

	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody InterestedApt interestedApt) throws Exception {
			interestedAptService.updateInterestedApt(interestedApt);
			System.out.println("수정");
			return new ResponseEntity<>("update", HttpStatus.OK);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> delte(@RequestBody InterestedApt interestedApt) throws Exception {
			interestedAptService.deleteInterestedApt(interestedApt);
			System.out.println("삭제");
			return new ResponseEntity<>("delete", HttpStatus.OK);
	}
}
