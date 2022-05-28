package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;


import com.example.appbanhang.R;
import com.example.appbanhang.model.User;
import com.example.appbanhang.utils.Utils;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtNameUserprofile,txtgioitinhprofile,txtngaysinhprofile,txtsdtprofile,txtemailproflie,txtdiachiprofile;
    AppCompatButton btndangxuat,btnthemanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        initView();
        initControl();
    }

    private void initControl() {
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

        Log.d( "log", Utils.user_curent.getNgaysinh()+ "" );
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

        txtsdtprofile = findViewById( R.id.txtsdtprofile );
        btndangxuat = findViewById( R.id.btndangxuat );
        btnthemanh = findViewById( R.id.butonthemanh );

    }
}