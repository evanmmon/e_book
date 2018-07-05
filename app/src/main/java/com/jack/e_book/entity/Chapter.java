package com.jack.e_book.entity;

import java.io.Serializable;

/**
 * 章节信息，包括标题和内容，及顺序
 * @author MJZ
 *
 */
public class Chapter implements Serializable{
	private int order;
	private String titleId;
	private String titleName;
	private String content;

	public Chapter() {
	}

	public Chapter(String content, int lineNum, int order, String titleId, String titleName) {
		this.content = content;
		this.lineNum = lineNum;
		this.order = order;
		this.titleId = titleId;
		this.titleName = titleName;
	}

	private int lineNum;

	@Override
	public String toString() {
		return "Chapter{" +
				"content='" + content + '\'' +
				", order=" + order +
				", titleId='" + titleId + '\'' +
				", titleName='" + titleName + '\'' +
				", lineNum=" + lineNum +
				'}';
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}


	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
