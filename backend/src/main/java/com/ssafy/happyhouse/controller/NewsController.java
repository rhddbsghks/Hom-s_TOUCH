package com.ssafy.happyhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.NewsDto;
import com.ssafy.happyhouse.model.service.NewsService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
@CrossOrigin("*")
public class NewsController {
	String clientId = "uLsx4YZaNFmTaH82Tqxo"; // 애플리케이션 클라이언트 아이디값"
	String clientSecret = "wk2zNn0x56"; // 애플리케이션 클라이언트 시크릿값"
	
	@Autowired
	private NewsService newsService;

	@GetMapping("/search/{keyword}")
	public String keywordNews(@PathVariable("keyword") String text) {
		System.out.println(text);
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		 
		String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text+"&display=5&sort=sim";
		System.out.println(apiURL);
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);

		System.out.println(responseBody);

		return responseBody;
	}
	
	
	@GetMapping("/recommend")
	public List<NewsDto> getNewsList()  throws Exception{
		return newsService.getNewsList();
	}
	
	@PostMapping("")
	public void registNews(@RequestBody NewsDto newsDto)  throws Exception{
//		System.out.println(newsDto);
		 newsService.registerNews(newsDto);
	}
	
	@DeleteMapping("/{no}")
	public void deleteNews(@PathVariable("no") int no)  throws Exception{
		 newsService.deleteNews(no);
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
}
