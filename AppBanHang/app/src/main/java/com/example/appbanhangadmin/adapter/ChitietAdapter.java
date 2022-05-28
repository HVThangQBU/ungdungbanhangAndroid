package com.example.appbanhangadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhangadmin.model.Item;
import com.example.appbanhangadmin.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyviewHolder> {

    Context context;
    List<Item> itemList;

    public ChitietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_chitiet_xemdonhang,parent,false );

        return new MyviewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Item item = itemList.get( position );
        holder.txtten.setText( item.getTensanpham() + " "  );
        holder.txtsoluong.setText(" Số lượng: " + item.getSoluong() + " " );
        if(item.getHinhanhsanpham().contains( "https" )){
            Glide.with(context).load(item.getHinhanhsanpham()).into(holder.imagechitiet);
        }else{
            String hinh = Utils.BASE_URL+"images/"+item.getHinhanhsanpham();
            Glide.with(context).load(hinh).into(holder.imagechitiet);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText( decimalFormat.format(item.getGiasanpham())+" ");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //item_chitietsqanpham
    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView imagechitiet;
        TextView txtten,txtsoluong,txtgia;
        public MyviewHolder(@NonNull View itemView) {
            super( itemView );
            imagechitiet = itemView.findViewById( R.id.item_imgchitietxemdonhang );
            txtten = itemView.findViewById( R.id.item_tensp_xemdonhang );
            txtsoluong = itemView.findViewById( R.id.item_sol_xemdonghang );
            txtgia = itemView.findViewById( R.id.item_giaxemdonhang );

        }
    }
}
