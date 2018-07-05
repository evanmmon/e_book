package com.jack.e_book.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.e_book.R;
import com.jack.e_book.entity.BookInfo;
import com.jack.e_book.utils.HttpUtil;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;

public class BookInfoActivity extends AppCompatActivity {
    private Button btnBack;
    private Button btnLoad;
    private TextView bookName;
    private TextView bookAuthor;
    private TextView bookUploadTime;
    private TextView bookSize;
    private TextView bookIntro;

    private BookInfo book;
    private ProgressDialog pd;;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
  //  private String loadPath = "http://192.168.1.200:8888/lixi_book_city/uploadtexts/baiHuaYiJing.txt";

    //private String key; = HttpUtil.getMD5(loadPath.getBytes());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_info);

        findView();
        initView();
    }

    private void initView() {
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        sp = getSharedPreferences("book_names",MODE_PRIVATE);
        editor = sp.edit();
        Intent intent = getIntent();
        book = (BookInfo) intent.getSerializableExtra("book");
        bookName.setText("书籍名称：   "+book.getBookname());
        bookAuthor.setText("作        者：   "+book.getBookauthor());
        bookUploadTime.setText("上传时间：   "+book.getUploadtime().substring(0,10));
        bookSize.setText("大        小：   "+book.getTextsize()+"M");
        bookIntro.setText("     "+book.getBookintro());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookInfoActivity.this,BookShopActivity.class));
                finish();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookInfoActivity.this,book.getBookurl(),Toast.LENGTH_SHORT).show();
                pd.setTitle("下载");
                pd.setMessage("正在下载请稍后。。。");
                pd.show();
               // HttpUtil.getData(BookInfoActivity.this, book.getBookurl(), null, new TextHttpResponseHandler() {
                HttpUtil.getData(BookInfoActivity.this, book.getBookurl(), null, new BinaryHttpResponseHandler(
                        new String[]{"text/plain","text/html;charset=utf-8","image/jpeg",
                        "image/png", "application/octet-stream"}) {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                //获取SD卡的下载目录
                                String directory = Environment.getExternalStorageDirectory()+"/MyReaderDownload";
                                //String directory =getFilesDir()+"/MyReaderDownload";
                                File file = new File(directory);
                                if(!file.exists()){
                                    file.mkdir();
                                }
                                Toast.makeText(BookInfoActivity.this,directory, Toast.LENGTH_SHORT).show();
                                try {
                                    FileOutputStream fos = new FileOutputStream(directory+"/"+book.getBookname()+".txt");
                                    fos.write(bytes);
                                    fos.close();
                                    pd.dismiss();
                                    Toast.makeText(BookInfoActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();

                                    //editor.putString(key,book.getBookname());
                                    //editor.commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }

                            @Override
                            public void onProgress(long bytesWritten, long totalSize) {
                                pd.setMax((int)totalSize);
                                pd.setProgress((int)bytesWritten);
                                super.onProgress(bytesWritten, totalSize);
                            }
                        }
                );
            }
        });


    }

    private void findView() {
        btnBack = (Button) findViewById(R.id.btn_bookInfo_back);
        btnLoad = (Button) findViewById(R.id.btn_bookInfo_load);
        bookName = (TextView) findViewById(R.id.text_bookInfo_bookName);
        bookAuthor = (TextView) findViewById(R.id.text_bookInfo_bookAuthor);
        bookUploadTime = (TextView) findViewById(R.id.text_bookInfo_bookUploadTime);
        bookSize = (TextView) findViewById(R.id.text_bookInfo_bookSize);
        bookIntro = (TextView) findViewById(R.id.text_bookInfo_bookIntro);
    }
}
