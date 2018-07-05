package com.jack.e_book.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jack.e_book.R;
import com.jack.e_book.adapter.BookLocalGridAdapter;
import com.jack.e_book.entity.Book;
import com.jack.e_book.entity.BookInfo;
import com.jack.e_book.thread.MyLoadbooks;
import com.jack.e_book.utils.Content;
import com.jack.e_book.utils.HttpUtil;
import com.jack.e_book.utils.SPUtils;
import com.jack.e_book.view.MyGridView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookHomeActivity extends AppCompatActivity {
    private MyGridView myGridView;
    private Button button;
    private List<BookInfo> list = new ArrayList<>();
    private boolean flag = false;
    private ProgressDialog pd;
    private String key;
    private Book book;
    private String[] bookNames;
    private Button btnAdd;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                book = (Book) msg.obj;
                pd.dismiss();
                toChapterIntent(book);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_home);
        myGridView = (MyGridView) findViewById(R.id.book_MyGridView);
        button = (Button) findViewById(R.id.btn_book_shop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookHomeActivity.this,BookShopActivity.class);
                startActivity(intent);
            }
        });
        btnAdd = (Button) findViewById(R.id.btn_book_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBooks();
            }
        });
        initView();

    }
    public void initView(){
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadBooks();
        //点击书籍图标显示的页面内容处理
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pd.setTitle("加载内容");
                pd.setMessage("正在加载，请稍后。。。");
                pd.show();
                BookInfo bookInfo = list.get(position);
                String bookName = bookInfo.getBookname();
                key = HttpUtil.getMD5(bookName.getBytes());
                Gson gson = new Gson();
                if(SPUtils.get(BookHomeActivity.this,key,"").equals("")){
                    File sourceFile = new File(bookInfo.getBookurl());
                    if(sourceFile!=null){
                        MyLoadbooks myLoadbooks = new MyLoadbooks(bookName,null,handler,sourceFile);
                        myLoadbooks.start();
                    }else {
                        Toast.makeText(BookHomeActivity.this,"您加载的书籍信息不存在！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    String json = (String) SPUtils.get(BookHomeActivity.this,key,"");
                    //Log.d("test",json);
                    Log.d("test",key+"-------------");
                    Type listType = new TypeToken<Book>() {}.getType();
                    Book book = gson.fromJson(json,listType);
                    pd.dismiss();
                    toChapterIntent(book);
                }
            }
        });

       myGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
               AlertDialog.Builder alert = new AlertDialog.Builder(BookHomeActivity.this);
               alert.setTitle("删除");
               alert.setMessage("要删除本地书籍吗？");
               alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String bookname = bookNames[position];
                       File temp = new File(SPUtils.DOWNLOAD_DIR+"/"+bookname);
                       if(book!=null){
                          temp.delete();
                           loadBooks();
                           Toast.makeText(BookHomeActivity.this,"已成功删除"+bookname+"书籍!",Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(BookHomeActivity.this,"删除"+bookname+"书籍失败!",Toast.LENGTH_SHORT).show();
                       }
                   }
               });
               alert.setNegativeButton("取消",null);
               alert.create().show();
               return true;
           }
       });
    }

    private void loadBooks() {
        File bookDirList = new File(SPUtils.DOWNLOAD_DIR);
        bookNames = bookDirList.list();
        list.clear();
        for(String filename:bookNames){
            BookInfo bookInfo = new BookInfo();
            String bookName = filename.substring(0,filename.indexOf("."));
            bookInfo.setBookname(bookName);
            bookInfo.setBookurl(SPUtils.DOWNLOAD_DIR+"/"+filename);
            list.add(bookInfo);
        }
        myGridView.setAdapter(new BookLocalGridAdapter(list,BookHomeActivity.this));
    }

    private void toChapterIntent(Book book) {
        if(book!=null){
            Intent toChapter = new Intent();
            toChapter.setClass(BookHomeActivity.this,ChapterInfoActivity.class);
            Content.getContent().setObject(book);
            startActivity(toChapter);
        }else{
            Toast.makeText(BookHomeActivity.this,"生成章节信息失败！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(flag){
            finish();
        }else{
            SPUtils.remove(BookHomeActivity.this,key);
            flag=true;
            Toast.makeText(BookHomeActivity.this,"在按一次退出",Toast.LENGTH_SHORT).show();
        }
    }
}
