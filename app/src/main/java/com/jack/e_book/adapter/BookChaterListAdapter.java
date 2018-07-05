package com.jack.e_book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jack.e_book.R;
import com.jack.e_book.entity.Chapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BookChaterListAdapter extends BaseAdapter {
    private Context context;
    private List<Chapter> list;

    public BookChaterListAdapter(Context context, List<Chapter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.chapter_list_item,null);
        }
        Chapter chapter = list.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.text_chapter_name);
        TextView content = (TextView) convertView.findViewById(R.id.text_chapter_content);
        name.setText(chapter.getTitleId());
        content.setText(chapter.getTitleName());
        return convertView;
    }
}
