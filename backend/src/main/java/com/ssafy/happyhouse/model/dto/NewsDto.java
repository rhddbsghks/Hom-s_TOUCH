package com.ssafy.happyhouse.model.dto;

public class NewsDto {
	private int no;
	private String link;
	private String thumbnail;
	private String title;
	private String regTime;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	@Override
	public String toString() {
		return "NewsDto [link=" + link + ", thumbnail=" + thumbnail + ", title=" + title + ", regTime=" + regTime + "]";
	}
}
