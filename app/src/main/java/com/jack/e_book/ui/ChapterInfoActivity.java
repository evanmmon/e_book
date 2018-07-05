package com.jack.e_book.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jack.e_book.R;
import com.jack.e_book.adapter.BookChaterListAdapter;
import com.jack.e_book.entity.Book;
import com.jack.e_book.entity.Chapter;
import com.jack.e_book.utils.Content;
import com.jack.e_book.utils.SPUtils;

import java.util.List;

public class ChapterInfoActivity extends AppCompatActivity {
    private ListView listView;
    private List<Chapter> list;
    private Book book;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chapter_info);
        book = (Book) Content.getContent().getObject();
        String mark = (String) SPUtils.get(ChapterInfoActivity.this,book.getBookname()+SPUtils.MARK_NAME,"");
        init();
        if(!mark.equals("")){
            Intent toViewBook = new Intent(ChapterInfoActivity.this,ViewBookActivity.class);
            String[] strs = mark.split(":");
            book.setPostion(Integer.parseInt(strs[0]));
            toViewBook.putExtra("pageNum",Integer.parseInt(strs[1]));
            Content.getContent().setObject(book);
            startActivity(toViewBook);
        }
    }

    private void init() {
        TextView textView = (TextView) findViewById(R.id.text_chapter_header);
        Log.d("name","-----"+book.getBookname());
        textView.setText(book.getBookname());
        list = book.getChapterList();
        listView = (ListView) findViewById(R.id.list_chapter);
        listView.setAdapter(new BookChaterListAdapter(ChapterInfoActivity.this,list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toViewBook = new Intent(ChapterInfoActivity.this,ViewBookActivity.class);
                book.setPostion(position);
                Content.getContent().setObject(book);
                startActivity(toViewBook);
            }
        });
    }
}

