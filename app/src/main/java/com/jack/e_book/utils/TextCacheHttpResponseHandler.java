package com.jack.e_book.utils;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 *  扩展TextHttpResponseHandler，增加缓存方法调用
 * Created by Administrator on 2016/10/25.
 */

public  abstract  class TextCacheHttpResponseHandler extends JsonHttpResponseHandler {

    public void onCache(String responseString){

    }
}
