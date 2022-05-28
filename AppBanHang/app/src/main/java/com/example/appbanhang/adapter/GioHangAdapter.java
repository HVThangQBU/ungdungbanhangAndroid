package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ImageClickListenner;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensanpham.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong() + " ");
        Glide.with(context).load(gioHang.getHinhanhsp()).into(holder.img_item_giohang);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp()))+"Đ");
        // fix gia
        long gia = gioHang.getSoluong() * gioHang.getGiasp() ;
        holder.item_giohang_giasoluongsp2.setText(decimalFormat.format(gia) );
        holder.checkBoxgiohang.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky( new TinhTongEvent() );
                }else{
                    for(int i = 0; i < Utils.mangmuahang.size(); i ++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky( new TinhTongEvent() );
                        }
                    }
                }
            }
        } );
        holder.setListenner( new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG","onImageClick: "+ pos +"...."+giatri);
                if (giatri ==1){
                    if(gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong() -1;
                        gioHangList.get(pos).setSoluong( soluongmoi );
                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasoluongsp2.setText(decimalFormat.format(gia) );
                        EventBus.getDefault().postSticky( new TinhTongEvent() );
                    } else if(gioHangList.get(pos).getSoluong() ==1 ){
                        AlertDialog.Builder builder = new AlertDialog.Builder( view.getRootView().getContext() );
                        builder.setTitle( "Thông báo" );
                        builder.setMessage( "Bạn có muốn xóa sản phẩm này khỏi giỏ hàng" );
                        builder.setPositiveButton( "Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove( pos );
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky( new TinhTongEvent() );
                            }
                        } );
                        builder.setNegativeButton( "Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        } );
                        builder.show();

                    }
                }else if (giatri ==2){
                    if(gioHangList.get(pos).getSoluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                        gioHangList.get(pos).setSoluong( soluongmoi );
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasoluongsp2.setText(decimalFormat.format(gia) );
                    EventBus.getDefault().postSticky( new TinhTongEvent() );
                }
            }
        } );

    }

    @Override
    public int getItemCount() {

        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_item_giohang,item_giohang_tru,item_giohang_cong;
        TextView item_giohang_tensanpham,item_giohang_gia,item_giohang_soluong,item_giohang_giasoluongsp2;
        ImageClickListenner listenner;
        CheckBox checkBoxgiohang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_giohang = itemView.findViewById(R.id.img_item_giohang );
            item_giohang_tensanpham = itemView.findViewById( R.id.item_giohang_tensanpham );
            item_giohang_gia = itemView.findViewById( R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById( R.id.item_giohang_soluong);
            item_giohang_giasoluongsp2 = itemView.findViewById( R.id.item_giohang_giasoluongsp2);
            item_giohang_tru = itemView.findViewById( R.id.item_giohang_tru );
            item_giohang_cong = itemView.findViewById( R.id.item_giohang_cong );
            checkBoxgiohang = itemView.findViewById( R.id.item_checkboxgiohang );
            // event click
            item_giohang_cong.setOnClickListener( this );
            item_giohang_tru.setOnClickListener( this );
        }

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_tru){
                listenner.onImageClick( view,getAdapterPosition(),1 );
            }else if(view == item_giohang_cong){
                listenner.onImageClick( view,getAdapterPosition(),2 );
            }
        }
    }
}
