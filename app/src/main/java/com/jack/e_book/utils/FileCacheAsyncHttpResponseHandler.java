package com.jack.e_book.utils;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;

/**
 * Created by Administrator on 2016/10/25.
 */
public abstract class FileCacheAsyncHttpResponseHandler extends FileAsyncHttpResponseHandler {

    public FileCacheAsyncHttpResponseHandler(File file) {
        super(file);
    }

    public void onCache(File f){

    }
}
