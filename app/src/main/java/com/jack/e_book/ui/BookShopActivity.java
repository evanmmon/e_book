package com.jack.e_book.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.e_book.R;
import com.jack.e_book.adapter.BookShopListAdapter;
import com.jack.e_book.entity.BookInfo;
import com.jack.e_book.utils.HttpUtil;
import com.jack.e_book.utils.SPUtils;
import com.jack.e_book.utils.TextCacheHttpResponseHandler;
import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private AsyncHttpClient httpClient;
    private BookShopListAdapter adapter;
    private Button btnBack;
    private List<BookInfo> books;

    private JSONArray jsonArray;
    //192.168.0.106
    private String key = HttpUtil.getMD5(HttpUtil.BASE_URL.getBytes());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_shop);
        btnBack = (Button) findViewById(R.id.btn_bookShop_back);
        listView = (ListView) findViewById(R.id.list_bookShop);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookShopActivity.this, BookHomeActivity.class));
                finish();
            }
        });


        TextCacheHttpResponseHandler textHadler = new TextCacheHttpResponseHandler() {
            @Override
            public void onStart() {
                Toast.makeText(BookShopActivity.this, "正在加载书籍信息", Toast.LENGTH_SHORT).show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                SPUtils.put(BookShopActivity.this, key, response);

                Type listType = new TypeToken<ArrayList<BookInfo>>() {
                }.getType();
                Gson gson = new Gson();
                books = gson.fromJson(response.toString(), listType);
                BookShopActivity.this.jsonArray = response;

                adapter = new BookShopListAdapter(books, BookShopActivity.this);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(BookShopActivity.this);
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(BookShopActivity.this, "加载书籍信息失败", Toast.LENGTH_SHORT).show();
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFinish() { // 完成后调用，失败，成功，都要掉
                Toast.makeText(BookShopActivity.this, "加载书籍信息结束", Toast.LENGTH_SHORT).show();
                super.onFinish();
            }
        };

        if (getSharedPreferences(SPUtils.FILE_NAME,MODE_PRIVATE).getString(key,"").equals("")) {
            HttpUtil.get(BookShopActivity.this,HttpUtil.BASE_URL, null, textHadler);
        } else {
            String response = (String) SPUtils.get(BookShopActivity.this, key, "");
            Type listType = new TypeToken<ArrayList<BookInfo>>() {
            }.getType();
            Gson gson = new Gson();
            books = gson.fromJson(response, listType);

            adapter = new BookShopListAdapter(books, BookShopActivity.this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(BookShopActivity.this);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        BookInfo bookInfo = books.get(position);
        intent.putExtra("book", bookInfo);
        intent.setClass(BookShopActivity.this, BookInfoActivity.class);
        startActivity(intent);
    }
}
