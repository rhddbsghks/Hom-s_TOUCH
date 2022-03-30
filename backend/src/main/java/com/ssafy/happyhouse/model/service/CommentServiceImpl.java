package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.CommentDto;
import com.ssafy.happyhouse.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public void registerComment(CommentDto comment) throws SQLException {
		commentMapper.registerComment(comment);
	}

	@Override
	public List<CommentDto> listComment(int noticeNo) throws Exception {
		return commentMapper.listComment(noticeNo);
	}

	@Override
	public void updateComment(CommentDto comment) throws Exception {
		commentMapper.updateComment(comment);
	}

	@Override
	public void deleteComment(int commentNo) throws Exception {
		commentMapper.deleteComment(commentNo);
	}
}
