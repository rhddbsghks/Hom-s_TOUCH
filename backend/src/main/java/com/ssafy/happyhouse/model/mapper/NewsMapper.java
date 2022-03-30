package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.NewsDto;

public interface NewsMapper {

	List<NewsDto> getNewsList() throws Exception;

	void registerNews(NewsDto newsDto) throws Exception;

	void deleteNews(int no) throws Exception;
}
