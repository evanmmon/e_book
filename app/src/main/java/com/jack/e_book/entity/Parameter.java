package com.jack.e_book.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/6.
 */
public class Parameter implements Serializable{
    private String op;
    private String bookName;
    private String file;
    private String charset;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }


    public Parameter( String bookName, String charset, String file, String op) {
        this.bookName = bookName;
        this.charset = charset;
        this.file = file;
        this.op = op;
    }
}
