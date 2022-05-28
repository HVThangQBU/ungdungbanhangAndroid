package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.GioHangAdapter;
import com.example.appbanhangadmin.model.EventBus.TinhTongEvent;
import com.example.appbanhangadmin.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    TextView txtgiohangtrong,txttongtiengiohang;
    Toolbar toolbargiohang;
    RecyclerView recyclerViewgiohang;
    Button buttonmuahang;
    GioHangAdapter adapter;
    long tongtiensp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_gio_hang );
        initView();
        tinhtongtien();
        initControl();
    }

    private void tinhtongtien() {
        tongtiensp =0;
        for(int i = 0; i < Utils.mangmuahang.size(); i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get( i ).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtiengiohang.setText( decimalFormat.format( tongtiensp ) );
    }

    private void initControl() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        recyclerViewgiohang.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerViewgiohang.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size() == 0){

            txtgiohangtrong.setVisibility(View.VISIBLE);
        }else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerViewgiohang.setAdapter(adapter);
        }
        buttonmuahang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                intent.putExtra( "tongtien",tongtiensp);
                if (Utils.mangmuahang.size() == Utils.manggiohang.size()){
                    Utils.manggiohang.clear();
                    startActivity( intent );
                }else {
                    for(int i = 0; i < Utils.manggiohang.size() ; i++){
                        for(int j = 0; j < Utils.mangmuahang.size(); j++){
                            if(Utils.manggiohang.get( i ) == Utils.mangmuahang.get(j)){
                                Utils.manggiohang.remove( i );
                                startActivity( intent );
                            }
                        }
                    }
                }
            //    Utils.manggiohang.clear();
            //    startActivity( intent );
            }
        } );
    }

    private void initView() {
        txtgiohangtrong = findViewById( R.id.txtgiohangtrong );
        toolbargiohang = findViewById( R.id.toolbargiohang);
        recyclerViewgiohang = findViewById( R.id.recycleviewgiohang );
        txttongtiengiohang = findViewById( R.id.txttonggiatiensanpham );
        buttonmuahang = findViewById( R.id.buttonmuahang );
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register( this );
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister( this );
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhtongtien();
        }
    }
}