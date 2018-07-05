package com.jack.e_book.entity;

import java.io.Serializable;

public class BookInfo implements Serializable{
    private int bookid;
    private String bookname;
    private String bookauthor;
    private String bookurl;
    private String uploadtime;
    private String bookintro;

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookauthor='" + bookauthor + '\'' +
                ", bookid=" + bookid +
                ", bookname='" + bookname + '\'' +
                ", bookurl='" + bookurl + '\'' +
                ", uploadtime='" + uploadtime + '\'' +
                ", bookintro='" + bookintro + '\'' +
                ", textsize=" + textsize +
                '}';
    }

    private double textsize; //文件大小

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getBookintro() {
        return bookintro;
    }

    public void setBookintro(String bookintro) {
        this.bookintro = bookintro;
    }

    public double getTextsize() {
        return textsize;
    }

    public void setTextsize(double textsize) {
        this.textsize = textsize;
    }


}

