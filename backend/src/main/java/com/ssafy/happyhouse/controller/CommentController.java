package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.CommentDto;
import com.ssafy.happyhouse.model.service.CommentService;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/{noticeNo}")
	public List<CommentDto> getNotice(@PathVariable("noticeNo") String noticeNo) throws Exception {
		return commentService.listComment(Integer.parseInt(noticeNo));
	}
	
	@PostMapping("")
	public void registerComment(@RequestBody CommentDto comment) throws Exception {
		commentService.registerComment(comment);
	}
	
	@PutMapping("")
	public void updateComment(@RequestBody CommentDto comment) throws Exception{
		commentService.updateComment(comment);
	}
	
	@DeleteMapping("/{commentNo}")
	public void deleteComment(@PathVariable("commentNo")int commentNo) throws Exception {
		commentService.deleteComment(commentNo);
	}
}
