package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.dto.NoticeParameterDto;
import com.ssafy.happyhouse.model.service.NoticeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/notice")
@CrossOrigin("*")
@Api(value="notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@PostMapping("")
	public void register(@RequestBody NoticeDto notice) throws Exception {
		noticeService.registerNotice(notice);
	}

	@GetMapping("")
	public ResponseEntity<List<NoticeDto>> listArticle( NoticeParameterDto noticeParameterDto) throws Exception {
		return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(noticeParameterDto), HttpStatus.OK);
	}
	
	@GetMapping("/notice")
	public List<NoticeDto> noticelist() throws Exception {
		return noticeService.notice();
	}
	 
	@GetMapping("/total")
	public int totalCnt() throws Exception {
		return noticeService.totalCnt();
	} 
	
	@GetMapping("/{noticeNo}")
	public NoticeDto getNotice(@PathVariable("noticeNo") String noticeNo) throws Exception {
		return noticeService.getNotice(Integer.parseInt(noticeNo));
	}
	
	@PutMapping("/{noticeNo}")
	public void updateNotice(@PathVariable("noticeNo") String noticeNo,@RequestBody NoticeDto notice) throws Exception {
		noticeService.updateNotice(notice);
	}
	
	@DeleteMapping("/{noticeNo}")
	public void deleteNotice(@PathVariable("noticeNo") String noticeNo) throws Exception {
		noticeService.deleteNotice(Integer.parseInt(noticeNo));
	}
}

