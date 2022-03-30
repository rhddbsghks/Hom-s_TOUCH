package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.NewsDto;

public interface NewsService {
	List<NewsDto> getNewsList() throws Exception;

	void registerNews(NewsDto newsDto) throws Exception;

	void deleteNews(int no) throws Exception;
}
