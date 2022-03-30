package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.CommentDto;
import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.dto.NoticeParameterDto;

public interface CommentService {

	void registerComment(CommentDto comment) throws SQLException;

	List<CommentDto> listComment(int noticeNo) throws Exception;

	void updateComment(CommentDto comment) throws Exception;

	void deleteComment(int commentNo) throws Exception;
}
