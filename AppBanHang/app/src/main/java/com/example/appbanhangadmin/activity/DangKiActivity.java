package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {
    EditText email,pass,repass,sdt,diachi,username;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dang_ki );
        initView();
        initControll();
    }

    private void initControll() {
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }
        } );
    }

    private void dangKi() {
        String str_email = email.getText().toString().trim();
        String str_usename = username.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_sdt = sdt.getText().toString().trim();
        String str_diachi = diachi.getText().toString().trim();
        if (TextUtils.isEmpty( str_email )  ){
            Toast.makeText( getApplicationContext(),"Bạn chưa nhập Email",Toast.LENGTH_LONG ).show();
        }
        else if (!isEmailValid( str_email )){
            Toast.makeText( getApplicationContext(),"Email nhập chưa đúng",Toast.LENGTH_LONG ).show();
        }
        else if (TextUtils.isEmpty( str_usename )) {
            Toast.makeText( getApplicationContext(), "Bạn chưa nhập Username", Toast.LENGTH_LONG ).show();
        }else if (TextUtils.isEmpty( str_pass )) {
            Toast.makeText( getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_LONG ).show();
        }else if (TextUtils.isEmpty( str_repass )){
            Toast.makeText( getApplicationContext(),"Bạn chưa nhập lại mật khẩu",Toast.LENGTH_LONG ).show();
        }else if (TextUtils.isEmpty( str_sdt )){
            Toast.makeText( getApplicationContext(),"Bạn chưa nhập số điện thoại",Toast.LENGTH_LONG ).show();
        }
        else if (TextUtils.isEmpty( str_diachi )){
            Toast.makeText( getApplicationContext(),"Bạn chưa nhập địa chỉ",Toast.LENGTH_LONG ).show();
        }else{
            if(str_pass.equals( str_repass )){
            // post data
                compositeDisposable.add(apiBanHang.dangki( str_email,str_pass,str_usename,str_sdt,str_diachi )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Utils.user_curent.setEmail( str_email );
                                Utils.user_curent.setPass( str_pass );
                                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                startActivity( intent );
                                finish();
                            }else{
                                Toast.makeText( getApplicationContext(),userModel.getMessage(),Toast.LENGTH_LONG ).show();
                            }

                        },throwable -> {
                            Toast.makeText( getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG ).show();
                        }
                ));


            }else{
                Toast.makeText( getApplicationContext(),"Mật khẩu bạn nhập không giống nhau",Toast.LENGTH_LONG ).show();
            }
        }




    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        email = findViewById( R.id.email );
        pass = findViewById( R.id.pass );
        repass = findViewById( R.id.repass );
        button = findViewById( R.id.btndangki );
        sdt = findViewById( R.id.sdt );
        diachi = findViewById( R.id.diachi );
        username = findViewById( R.id.usename );
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}