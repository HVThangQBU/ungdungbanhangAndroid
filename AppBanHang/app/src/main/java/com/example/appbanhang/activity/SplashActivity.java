package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanhang.R;
import com.example.appbanhang.model.User;
import com.example.appbanhang.utils.Utils;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        Paper.init(this);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep( 1500 );
                }catch (Exception ex){

                }finally {
                    if(Paper.book().read( "user" ) == null){
                        Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity( intent );
                        finish();
                    }else{
                        Intent home = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity( home );
                        User user = Paper.book().read( "user" );
                        Utils.user_curent = user;
                        finish();
                    }

                }
            }
        };
        thread.start();
    }
}