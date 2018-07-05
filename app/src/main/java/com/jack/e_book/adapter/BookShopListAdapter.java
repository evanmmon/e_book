package com.jack.e_book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jack.e_book.R;
import com.jack.e_book.entity.BookInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
public class BookShopListAdapter extends BaseAdapter {
    private Context context;
    private List<BookInfo> list;

    public List<BookInfo> getList() {
        return list;
    }

    public void setList(List<BookInfo> list) {
        this.list = list;
    }

    public BookShopListAdapter(List<BookInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
       if(view==null){
           view = LayoutInflater.from(context).inflate(R.layout.book_shop_list_item,null);
       }else {
           view = convertView;
       }
        BookInfo bookInfo = list.get(position);
        TextView bookName = (TextView) view.findViewById(R.id.text_bookShop_list_item_name);
        TextView bookAuthor = (TextView) view.findViewById(R.id.text_bookShop_list_item_author);
        TextView bookSize = (TextView) view.findViewById(R.id.text_bookShop_list_item_textSize);
        bookName.setText("书名："+bookInfo.getBookname());
        bookAuthor.setText("作者："+bookInfo.getBookauthor());
        bookSize.setText("大小："+bookInfo.getTextsize()+"M");
        return view;
    }
}
