package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.InterestedApt;

public interface InterestedAptService {

	List<InterestedApt> getInterestedApt(String id) throws Exception;
	void insertInterestedApt(InterestedApt interestedApt) throws Exception;
	void deleteInterestedApt(InterestedApt interestedApt) throws Exception;
	void updateInterestedApt(InterestedApt interestedApt) throws Exception;
}
