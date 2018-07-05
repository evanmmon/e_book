package com.jack.e_book.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.jack.e_book.R;

/**
 * Created by Administrator on 2016/10/23.
 */
public class MyGridView extends GridView {
    private Bitmap bitmap;

    public MyGridView(Context context,AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bookshelf_layer_center);
    }

    @Override
    // 绘制背景图片
    protected void dispatchDraw(Canvas canvas) {
        //获取GridView控件里面的高度
        int count = getChildCount();
        int top = 0;
        if (count > 0) {
            top = getChildAt(0).getTop();
        }
        int bitmapWeight = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight()+1;

        int parentWeight = getWidth();
        int parentHeight = getHeight();

        for (int y = top; y <= parentHeight; y += bitmapHeight) {
            for (int x = 0; x <= parentWeight; x += bitmapWeight) {
                canvas.drawBitmap(bitmap, x, y, null);
            }
        }

        super.dispatchDraw(canvas);
    }
}
