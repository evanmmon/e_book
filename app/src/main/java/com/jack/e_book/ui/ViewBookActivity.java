package com.jack.e_book.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.jack.e_book.R;
import com.jack.e_book.entity.Book;
import com.jack.e_book.entity.Chapter;
import com.jack.e_book.utils.BookPage;
import com.jack.e_book.utils.Content;
import com.jack.e_book.utils.SPUtils;
import com.jack.e_book.view.PageWidget;

import java.util.List;

public class ViewBookActivity extends AppCompatActivity {
    private PageWidget pageWidget;
    private Bitmap curBitmap, nextBitmap;
    private Canvas curCanvas, nextCanvas;
    private BookPage bookpage;
    private Book book;
    private List<Chapter> list;
    private  int pageNum;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_book);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pageNum = intent.getIntExtra("pageNum",0);

        book = (Book) Content.getContent().getObject();
          //Log.d("tag",book.toString());
        if(pageNum!=0){
            flag=true;
        }

        if(book!=null){
            initViewBook();
            initPageTurn();
        }

    }

    private void initPageTurn() {
        pageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                boolean toAutoChapter = true;
                //MotionEvent.ACTION_DOWN      //按下 = 0
                // MotionEvent.ACTION_MOVE:  //移动 = 2
               // MotionEvent.ACTION_UP:    // 抬起 = 1

                if(v==pageWidget){
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        pageWidget.abortAnimation();
                        pageWidget.calcCornerXY(event.getX(),event.getY());

                        bookpage.draw(curCanvas);
                        if(pageWidget.DragToRight()){
                            if(bookpage.prePage())
                                bookpage.draw(nextCanvas);
                            else
                                return false;
                        }else{
                            if(bookpage.nextPage())
                                bookpage.draw(nextCanvas);
                            else
                                return false;
                        }
                        pageWidget.setBitmaps(curBitmap,nextBitmap);
                    }
                    ret = pageWidget.doTouchEvent(event);
                    return ret;
                }
                return false;
            }
        });

        /*pageWidget.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewBookActivity.this);
                alert.setTitle("加入书签");
               // alert.setMessage("是否将当前页面（"+(offset+1)+"）加入到书签？");
                return false;
            }
        });*/
    }

    private void initViewBook(){
        //DisplayMetrics是一个可以获取安卓手机关于显示相应信息的类，如显示大小，分辨率，以及字体
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        curBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        nextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        System.out.println(w + "\t" + h);

        curCanvas = new Canvas(curBitmap);
        nextCanvas = new Canvas(nextBitmap);

        list = book.getChapterList();
        Chapter chapter = list.get(book.getPostion());
        bookpage = new BookPage(w, h,chapter);
        if(flag) {
            bookpage.setPageNum(pageNum);
        }
        bookpage.setBgBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.bg));

        bookpage.draw(curCanvas);

        pageWidget = new PageWidget(ViewBookActivity.this, w, h);
        setContentView(pageWidget);
        pageWidget.setBitmaps(curBitmap, curBitmap);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "关于");
        menu.add(0, 1, 1,"保存书签");
        menu.add(0, 1, 2,"清除书签");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String key = book.getBookname()+SPUtils.MARK_NAME;
        if(item.getOrder()==1){
            String order = bookpage.getOrder()+"";
            String pageNumStr = bookpage.getPageNum()+"";
            SPUtils.put(ViewBookActivity.this,key,order+":"+pageNumStr);
            Toast.makeText(ViewBookActivity.this,"成功保存第"+order+"章,第"+pageNumStr+"页的书签",Toast.LENGTH_SHORT).show();
        }else if(item.getOrder()==2){
            String mark = (String) SPUtils.get(ViewBookActivity.this,key,"");
            if(!mark.equals("")) {
                String[] strs = mark.split(":");
                Toast.makeText(ViewBookActivity.this, "成功清除第" + strs[0] + "章,第" + strs[1] + "页的书签", Toast.LENGTH_SHORT).show();
                SPUtils.remove(ViewBookActivity.this,key);
            }else {
                Toast.makeText(ViewBookActivity.this, "未保存书签,清除书签失败！", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

}
