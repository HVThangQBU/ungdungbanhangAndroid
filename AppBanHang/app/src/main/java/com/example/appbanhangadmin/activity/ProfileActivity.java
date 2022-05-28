package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appbanhang.R;
import com.example.appbanhangadmin.utils.Utils;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgprofile;
    TextView txtNameUserprofile,txtgioitinhprofile,txtngaysinhprofile,txtsdtprofile,txtemailproflie,txtdiachiprofile;
    AppCompatButton btndangxuat,btnthemanh;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        Paper.init( this );
        initView();
        initControl();
    }

    private void initControl() {

        Log.d( "log", "initControl: "+Utils.user_curent.getId() );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        txtNameUserprofile.setText( Utils.user_curent.getUsername() );
        txtemailproflie.setText( Utils.user_curent.getEmail() );
        txtsdtprofile.setText( Utils.user_curent.getSdt() );
        txtdiachiprofile.setText( Utils.user_curent.getDiachi() );
        txtngaysinhprofile.setText( Utils.user_curent.getNgaysinh() );
        if(Utils.user_curent.getHinhanh().contains( "https" )){
            Glide.with(getApplicationContext()).load(Utils.user_curent.getHinhanh()).into(imgprofile);
        }else{
            String hinh = Utils.BASE_URL+"imagesUser/"+Utils.user_curent.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).diskCacheStrategy(  DiskCacheStrategy.NONE).skipMemoryCache( true ).into(imgprofile);
        }
        Log.d( "log", Utils.user_curent.getHinhanh()+ "" );
        if (Utils.user_curent.getGioitinh() == 0){
            txtgioitinhprofile.setText( "nữ" );
        }else {
            txtgioitinhprofile.setText( "Nam" );
        }



        btndangxuat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().delete( "user" );

                Intent dangnhap = new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(dangnhap);
                finish();
            }
        } );
        btnthemanh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent themanh = new Intent(getApplicationContext(),ThemArnhActivity.class);
                startActivity(themanh);
            }
        } );

    }

    @Override
    protected void onResume() {
        super.onResume();
        initControl();
    }

    private void initView() {
        toolbar = findViewById( R.id.toolbarprofile );
        txtNameUserprofile = findViewById( R.id.txtNameUserprofile );
        txtngaysinhprofile = findViewById( R.id.txtngaysinhprofile );
        txtdiachiprofile = findViewById( R.id.txtdiachiprofile );
        txtemailproflie = findViewById( R.id.txtemailproflie );
        txtgioitinhprofile = findViewById( R.id.txtgioitinhprofile );
        imgprofile = findViewById( R.id.imgprofile );
        txtsdtprofile = findViewById( R.id.txtsdtprofile );
        btndangxuat = findViewById( R.id.btndangxuat );
        btnthemanh = findViewById( R.id.butonthemanh );

    }
}