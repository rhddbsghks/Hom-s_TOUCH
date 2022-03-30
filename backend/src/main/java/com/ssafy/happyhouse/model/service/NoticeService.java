package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.dto.NoticeParameterDto;

public interface NoticeService {

	void registerNotice(NoticeDto notice) throws SQLException;

	List<NoticeDto> listNotice(NoticeParameterDto noticeParameterDto) throws Exception;

	List<NoticeDto> notice() throws Exception;

	NoticeDto getNotice(int noticeNo) throws Exception;

	void updateNotice(NoticeDto notice) throws Exception;

	void deleteNotice(int noticeNo) throws Exception;

	void updateView(int noticeNo) throws Exception;
	
	int totalCnt() throws Exception;
}
