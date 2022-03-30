package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.InterestedApt;

public interface InterestedAptMapper {
	List<InterestedApt> getInterestedApt(String id) throws SQLException;
	void insertInterestedApt(InterestedApt interestedApt) throws SQLException;
	void deleteInterestedApt(InterestedApt interestedApt) throws SQLException;
	void updateInterestedApt(InterestedApt interestedApt) throws SQLException;
}
