package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.NoticeParameterDto;
import com.ssafy.happyhouse.model.dto.User;

public interface UserService {

	void insertUser(User user) throws SQLException;

	User login(User user) throws SQLException;

	User searchInfo(String name, String email) throws SQLException;

	void updateUser(User user) throws SQLException;

	void deleteUser(String user_id) throws SQLException;
	
	int idCheck(String userId) throws Exception;
	
	public User userInfo(String id) throws Exception;
	
	List<User> getUserList(NoticeParameterDto noticeParameterDto) throws Exception;
	
	int totalCnt() throws Exception;
}
