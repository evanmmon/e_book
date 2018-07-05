package com.jack.e_book.entity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 这个类包含书的信息，书名，作者，章节名称。
 * 由于这个类在本工程中仅需要实例化一次，因此将它设为单例。
 * 其这个类的唯一对象软件启动时被初始化，在关闭软件之前一般是不会发生了。
 * @author MJZ
 *
 */
public class Book implements Serializable{
	private String bookname;
	private String author;
	private File bookurl;
	public File getBookurl() {
		return bookurl;
	}

	public void setBookurl(File bookurl) {
		this.bookurl = bookurl;
	}

	private int postion;
	private int maxLineNum;

	public int getMaxLineNum() {
		return maxLineNum;
	}

	public void setMaxLineNum(int maxLineNum) {
		this.maxLineNum = maxLineNum;
	}

	public int getPostion() {
		return postion;
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	private List<Chapter> chapterList;
	
	private static Book book = new Book();
	
	private Book(){}
	
	public static Book getInstance(){
		return book;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<Chapter> chapterList) {
		this.chapterList = chapterList;
	}

	public Chapter getChapter(int index) {
			chapterList = getChapterList();
		if (index<0||index>getChapterList().size()-1) {
			return null;
		}
		return chapterList.get(index);
	}

	@Override
	public String toString() {
		return "Book{" +
				"author='" + author + '\'' +
				", bookname='" + bookname + '\'' +
				", bookurl='" + bookurl + '\'' +
				", postion=" + postion +
				", maxLineNum=" + maxLineNum +
				", chapterList=" + chapterList +
				'}';
	}
}
