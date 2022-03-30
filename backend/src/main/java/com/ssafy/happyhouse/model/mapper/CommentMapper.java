package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.CommentDto;

public interface CommentMapper {
	
	void registerComment(CommentDto comment) throws SQLException;

	List<CommentDto> listComment(int noticeNo) throws Exception;

	void updateComment(CommentDto comment) throws Exception;

	void deleteComment(int commentNo) throws Exception;
}
