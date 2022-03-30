package com.ssafy.happyhouse.model.dto;

public class CommentDto {
	private int commentNo;
	private int noticeNo;
	private String id;
	private String name;
	private String content;
	private String regTime;
	
	

	public CommentDto() {
		super();
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
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
	
	
	
	@Override
	public String toString() {
		return "CommentDto [commentNo=" + commentNo + ", noticeNo=" + noticeNo + ", id=" + id + ", name=" + name
				+ ", content=" + content + ", regTime=" + regTime + "]";
	}
}
