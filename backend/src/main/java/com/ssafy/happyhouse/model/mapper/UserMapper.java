package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import com.ssafy.happyhouse.model.dto.NoticeParameterDto;
import com.ssafy.happyhouse.model.dto.User;

public interface UserMapper {

	void insertUser(User user) throws SQLException;

	User login(User user) throws SQLException;
	
	 User userInfo(String id) throws SQLException;

	User searchInfo(String name, String email) throws SQLException;

	void updateUser(User user) throws SQLException;

	void deleteUser(String user_id) throws SQLException;

	int idCheck(String userId) throws Exception;
	
	List<User> getUserList(NoticeParameterDto onoticeParameterDto) throws Exception;
	
	int totalCnt() throws Exception;

}
