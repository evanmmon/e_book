package com.jack.e_book.thread;

import android.os.Handler;
import android.os.Message;

import com.jack.e_book.entity.Book;
import com.jack.e_book.utils.IOHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MyLoadbooks extends Thread {
    private String bookName;
    private File sourceFile;
    private String encoding;
    private Handler handler;
    private Book book;

    public MyLoadbooks(String bookName, String encoding, Handler handler, File sourceFile) {
        this.bookName = bookName;
        this.encoding = encoding;
        this.handler = handler;
        this.sourceFile = sourceFile;
    }

    @Override
    public void run() {
        try {
           book =  IOHelper.getInstence().getBook(bookName,sourceFile,encoding);
            if(book!=null){
                /*
                List<Chapter> lists = book.getChapterList();
                for(Chapter c : lists) {
                    Log.d("tests", "-"+c.getLineNum()+"-" +c.getContent() );
                }*/
                Message message = handler.obtainMessage(1,book);
                message.sendToTarget();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
