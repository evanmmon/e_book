package com.jack.e_book.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/10/25.
 */
public class HttpUtil {
    private  static final String BASE_IP = "localhost";
    public static final String BASE_URL = "http://"+BASE_IP+":8888/lixi_book_city/Interface_BookList";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void setTimeout() {

        client.setTimeout(60000);
    }

    public static void get(Context context, String url, RequestParams params, TextCacheHttpResponseHandler responseHandler) {
        // 检查缓存是否存在，存在再调用responseHandler的onCache方法
        getPreferencesCacheData(context, url, responseHandler);
        client.get(url, params, (ResponseHandlerInterface) responseHandler);
    }

    public static void getData(Context context, String url, RequestParams params, BinaryHttpResponseHandler responseHandler) {
        // 检查缓存是否存在，存在再调用responseHandler的onCache方法
        client.get(url, params, responseHandler);
        client.setURLEncodingEnabled(true);
    }

    public static void post(Context context, String url, RequestParams params, TextCacheHttpResponseHandler responseHandler) {
        getPreferencesCacheData(context, url, responseHandler);
        client.post(url, params, (ResponseHandlerInterface) responseHandler);
    }

    public static void download(Context context, String url, RequestParams params, FileCacheAsyncHttpResponseHandler fileAsyncHttpResponseHandler) {
        // 检查文件是否存在，存在再调用fileAsyncHttpResponseHandler的onCache方法
        getFileCacheData(context, url, fileAsyncHttpResponseHandler);
        client.get(url, params, fileAsyncHttpResponseHandler);
    }

    private static boolean getPreferencesCacheData(Context context, String key, TextCacheHttpResponseHandler responseHandler) {
        key = getMD5(key.getBytes());
        String cache = String.valueOf(SPUtils.get(context, key, ""));
        if (!cache.equals("")) {
            responseHandler.onCache(cache);
            return true;
        } else {
            return false;
        }
    }

    private static boolean getFileCacheData(Context context, String key, FileCacheAsyncHttpResponseHandler responseHandler) {
        key = getMD5(key.getBytes());
        String filePath = String.valueOf(SPUtils.get(context, key, ""));
        if (!filePath.equals("")) {
            File file = new File(filePath);
            if (file.exists() == true) {
                responseHandler.onCache(file);
                return true;
            }
        }
        return false;
    }

    public static String getMD5(byte[] src) {
        StringBuffer sb = new StringBuffer();
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src);
            for (byte b : md.digest()) {
                sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(Integer.toString(b & 0xF, 16));
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }

}
