package com.ssafy.happyhouse.model.dto;

public class NoticeDto {
	private int noticeNo;
	private String id;
	private String name;
	private String subject;
	private String content;
	private String regTime;
	private int view;

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "NoticeDto [noticeNo=" + noticeNo + ", id=" + id + ", name=" + name + ", subject=" + subject
				+ ", content=" + content + ", regTime=" + regTime + ", view=" + view + "]";
	}
}
