package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.InterestedApt;
import com.ssafy.happyhouse.model.mapper.InterestedAptMapper;

@Service
public class InterestedAptServiceImpl implements InterestedAptService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<InterestedApt> getInterestedApt(String id) throws Exception {
		return sqlSession.getMapper(InterestedAptMapper.class).getInterestedApt(id);
	}

	@Override
	public void insertInterestedApt(InterestedApt interestedApt) throws Exception {
		sqlSession.getMapper(InterestedAptMapper.class).insertInterestedApt(interestedApt);
		
	}
	@Override
	public void deleteInterestedApt(InterestedApt interestedApt) throws Exception {
		sqlSession.getMapper(InterestedAptMapper.class).deleteInterestedApt(interestedApt);
	}

	@Override
	public void updateInterestedApt(InterestedApt interestedApt) throws Exception {
		sqlSession.getMapper(InterestedAptMapper.class).updateInterestedApt(interestedApt);
		
	}


}
