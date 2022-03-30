package com.ssafy.happyhouse.model.service;

import java.util.List;
import org.apache.commons.text.StringEscapeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.NewsDto;
import com.ssafy.happyhouse.model.mapper.NewsMapper;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	NewsMapper newsMapper;

	@Override
	public List<NewsDto> getNewsList() throws Exception {
		return newsMapper.getNewsList();
	}

	@Override
	public void registerNews(NewsDto newsDto) throws Exception {
		newsDto.setTitle(StringEscapeUtils.unescapeHtml4(newsDto.getTitle()));

		newsMapper.registerNews(newsDto);
	}

	@Override
	public void deleteNews(int no) throws Exception {
		newsMapper.deleteNews(no);
	}
}
