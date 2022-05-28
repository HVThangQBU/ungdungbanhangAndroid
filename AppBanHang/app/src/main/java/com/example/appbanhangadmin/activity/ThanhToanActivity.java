package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien, txtsdt,txtemail,txtdiachi;
    AppCompatButton btndathang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thanh_toan );
        initView();
        initControl();
        countItem();
    }

    private void countItem() {
        totalItem = 0;
        for(int i = 0; i < Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra( "tongtien",0 );
        txttongtien.setText(  decimalFormat.format( tongtien ));
        txtemail.setText( Utils.user_curent.getEmail() );
        txtsdt.setText( Utils.user_curent.getSdt() );
        txtdiachi.setText( Utils.user_curent.getDiachi() );

        btndathang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = Utils.user_curent.getEmail();
                String str_sdt = Utils.user_curent.getSdt();
                int iduser = Utils.user_curent.getId();
                String str_diachi = Utils.user_curent.getDiachi();
                // post data
                    Log.d("test",new Gson().toJson( Utils.mangmuahang ) );
                compositeDisposable.add( apiBanHang.createOder( str_email,str_sdt, String.valueOf( tongtien )
                        ,iduser,str_diachi,totalItem,new Gson().toJson( Utils.mangmuahang ) )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        userModel -> {
                            Toast.makeText( getApplicationContext(),"Thanh cong",Toast.LENGTH_SHORT ).show();
                            Utils.mangmuahang.clear();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity( intent );
                            finish();
                        },
                        throwable -> {
                            Toast.makeText( getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT ).show();
                        }
                ));

            }
        } );
    }

    private void initView() {
        toolbar = findViewById( R.id.toolbarthanhtoan );
        txtemail = findViewById( R.id.txtemail );
        txtsdt = findViewById( R.id.txtsdt );
        //them
        txtdiachi = findViewById( R.id.txtdiachi );
        txttongtien = findViewById( R.id.txttongtienthanhtoan );
        btndathang = findViewById( R.id.btndathang );
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL).create( ApiBanHang.class );

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}