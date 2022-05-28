package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp,giasp,motasp;
    Button btnthemvaogiohang,btnmuangaychitiet;
    ImageView imghinhanh;
    AppCompatButton btndongy;
    Spinner spinner;
    Toolbar toolbarchitetsanpham;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
        initMuangay();
    }

    private void initMuangay() {

        btnmuangaychitiet.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogSoluongmuahang();
            }
        } );
    }
    private void DialogSoluong(){
        Dialog dialog = new Dialog( this );
        dialog.setContentView( R.layout.dialog_customsl );
        spinner = dialog.findViewById(R.id.spinner);
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter( adapterspin );
        btndongy = dialog.findViewById( R.id.buttondongy );

        btndongy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
                dialog.dismiss();
            }
        } );
        Window window= dialog.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes ();
        layoutParams.gravity= Gravity.BOTTOM;
        window.setAttributes (layoutParams);
        dialog.show();
    }
    private void DialogSoluongmuahang(){
        Dialog dialog = new Dialog( this );
        dialog.setContentView( R.layout.dialog_customsl );
        spinner = dialog.findViewById(R.id.spinner);
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter( adapterspin );
        btndongy = dialog.findViewById( R.id.buttondongy );

        btndongy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muaGioHang();
                dialog.dismiss();
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        } );
        Window window= dialog.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes ();
        layoutParams.gravity= Gravity.BOTTOM;
        window.setAttributes (layoutParams);
        dialog.show();
    }
    private void initControl() {
        btnthemvaogiohang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSoluong();
            }
        } );
    }
    private void muaGioHang(){
        if(Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString() );
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGiasanpham());
                    Utils.manggiohang.get(i).setGiasp( gia );
                    flag = true;
                }
            }
            if(flag == false){

                long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) ;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensanpham());
                gioHang.setHinhanhsp(sanPhamMoi.getHinhanhsanpham());
                Utils.manggiohang.add(gioHang);
            }

        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) ;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId() );
            gioHang.setTensp(sanPhamMoi.getTensanpham());
            gioHang.setHinhanhsp(sanPhamMoi.getHinhanhsanpham() );
            Utils.manggiohang.add(gioHang);

        }
        Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
        startActivity(giohang);
    }
    private void themGioHang() {

        if(Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString() );
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong( sanPhamMoi.getGiasanpham() );
                    Utils.manggiohang.get(i).setGiasp( gia );
                    flag = true;

                }
            }
            if(flag == false){
                //   long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * soluong;
                long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) ;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensanpham());
                gioHang.setHinhanhsp(sanPhamMoi.getHinhanhsanpham());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
        //    long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * soluong;
            long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * 1 ;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId() );
            gioHang.setTensp(sanPhamMoi.getTensanpham());
            gioHang.setHinhanhsp(sanPhamMoi.getHinhanhsanpham() );
            Utils.manggiohang.add(gioHang);

        }
        int totalItem = 0;

        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf( totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra( "chitiet" );
        tensp.setText(sanPhamMoi.getTensanpham() );
        motasp.setText(sanPhamMoi.getMotasanpham());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanhsanpham()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham()))+"Đ");
//        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
//        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
//        spinner.setAdapter( adapterspin );
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarchitetsanpham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitetsanpham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem) );
        }
    }

    private void initView() {
        tensp = findViewById(R.id.txttensannpham);
        giasp = findViewById(R.id.txtgiasanpham);
        motasp = findViewById(R.id.txtmotachitiet);
        btnthemvaogiohang = findViewById(R.id.btnthemvaogiohang);
        btnmuangaychitiet = findViewById( R.id.btnmuangaychitiet );
    //    spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitietsanpham);
        toolbarchitetsanpham= findViewById(R.id.toolbarchitietsanpham);
        badge = findViewById( R.id.menu_soluong );
        FrameLayout frameLayoutgiohang = findViewById( R.id.framegiohangchitiet );
        frameLayoutgiohang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        } );
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem) );
        }
    }

}