package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Loaisp;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    List<Loaisp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<Loaisp> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView texttenloaisp;
        ImageView imghinhanhloaisp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisanpham,null);
            viewHolder.texttenloaisp = view.findViewById(R.id.item_tenloaisp);
            viewHolder.imghinhanhloaisp = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.texttenloaisp.setText(array.get(i).getTenloaisanpham());
        Glide.with(context).load(array.get(i).getHinhanhloaisanpham()).into(viewHolder.imghinhanhloaisp);
        return view;
    }
}
