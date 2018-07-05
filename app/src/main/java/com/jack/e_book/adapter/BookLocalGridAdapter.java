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
public class BookLocalGridAdapter extends BaseAdapter {
    private Context context;
    private List<BookInfo> list;

    public BookLocalGridAdapter(List<BookInfo> list, Context context) {
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
           view = LayoutInflater.from(context).inflate(R.layout.book_local_list_item,null);
       }else {
           view = convertView;
       }
        BookInfo bookInfo = list.get(position);
        TextView bookName = (TextView) view.findViewById(R.id.book_local_list_item_bookname);
        TextView bookOpenPath = (TextView) view.findViewById(R.id.book_local_list_item_bookurl);
        bookName.setText(bookInfo.getBookname());
        bookOpenPath.setText(bookInfo.getBookurl());

        return view;
    }
}
