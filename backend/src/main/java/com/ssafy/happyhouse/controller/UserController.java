package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.dto.NoticeParameterDto;
import com.ssafy.happyhouse.model.dto.User;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;

	@Autowired
	private JwtServiceImpl jwtService;

	@GetMapping("/idCheck")
	public int idCheck( String ckid) throws Exception {
		System.out.println(ckid);
		int cnt = 1;
		cnt = userService.idCheck(ckid);
		return cnt;
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<User>> getUserList( NoticeParameterDto noticeParameterDto) throws Exception {
		return new ResponseEntity<List<User>>(userService.getUserList(noticeParameterDto), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			User loginUser = userService.login(user);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getId(), "access-token");// key, data, subject
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("id") String id, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		if (jwtService.isUsable(request.getHeader("access-token"))) {
			try {
//				로그인 사용자 정보.
				User user = userService.userInfo(id);
				resultMap.put("userInfo", user);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/searchInfo")
	public User searchInfo(String name, String email) throws Exception {

		User user = userService.searchInfo(name, email);

		if (user == null) {
			return null;
		}
		return user;  
	}

	@PostMapping("")
	public String register(@RequestBody User user) throws Exception {
		userService.insertUser(user);
		return "success";
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") String id,@RequestBody User user)
			throws Exception {
		user.setId(id);
		userService.updateUser(user);
		return new ResponseEntity<>("update", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable("id") String id) throws Exception {
			userService.deleteUser(id);
			System.out.println("삭제");
			return new ResponseEntity<>("delete", HttpStatus.OK);
	}
	
	@GetMapping("/total")
	public int totalCnt() throws Exception {
		return userService.totalCnt();
	} 
}
