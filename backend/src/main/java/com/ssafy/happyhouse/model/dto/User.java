package com.ssafy.happyhouse.model.dto;

import java.util.ArrayList;

public class User {
	// 해당 회원의 ID
	private String id;
	
	// 해당 회원의 비밀번호
	private String pw;
	
	// 해당 회원의 이름
	private String name;
	
	// 해당 회원의 이메일
	private String email;

	
	// 해당 회원의 관심지역
	private ArrayList<InterestedPlace> interList;

	public User() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public void addInterPlace(InterestedPlace ip) {
		String dc = ip.getDongcode();
		
		for (int i = interList.size() - 1; i >= 0; i--) {
			if (interList.get(i).getDongcode().equals(dc)) {
				return;
			}
		}
		
		interList.add(ip);
	}
	
	public void removeInterPlace(InterestedPlace ip) {
		String dc = ip.getDongcode();
		
		for (int i = interList.size() - 1; i >= 0; i--) {
			if (interList.get(i).getDongcode().equals(dc)) {
				interList.remove(i);
				break;
			}
		}
	}

	@Override
	public String toString() {
		return "ID: " + id + "\tPW: " + pw + "\t이름: " + name
				+ "\t이메일: " + email;
		
//		this.dongcode = dongcode;
//		this.addrOther = addrOther;
	}	
}
