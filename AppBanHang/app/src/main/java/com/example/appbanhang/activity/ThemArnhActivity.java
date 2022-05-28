package com.example.appbanhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemArnhActivity extends AppCompatActivity {
    AppCompatButton btnxacnhanthem,btnhuythem,btnthemanh;
    ImageButton btnimgcamera;
    ImageView imgthemanhprofile;
    Toolbar toolbar;
    EditText edtsuaten,edtsuaemail,edtsuangaysinh,edtsuasodienthoai,edtsuadiachi;
    RadioGroup radioGroup;
    RadioButton rdButtonnu,rdButtonnam;
    AppCompatButton buttonsua;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    int isGioitinh = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_them_arnh );
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        initView();
        initControl();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK &&data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
             imgthemanhprofile.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream( uri );
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgthemanhprofile.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    private void initControl() {
        btnimgcamera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent, REQUEST_CODE_CAMERA );
            }
        } );
        btnthemanh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType( "image/*" );
                startActivityForResult( intent,REQUEST_CODE_FOLDER );
            }
        } );

        btnxacnhanthem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               updateInfo();
            }
        } );
        btnhuythem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(profile);
                finish();
            }
        } );



        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    private void updateInfo() {
        EditText edtsuatenprofile = findViewById( R.id.edtsuatenprofile );
        EditText edtngaysinhprofile =  findViewById( R.id.edtngaysinhprofile );
        EditText edttsdtprofile =  findViewById( R.id.edttsdtprofile );
        EditText edtdiachi =  findViewById( R.id.edtdiachi );


        String ngay = edtngaysinhprofile.getText().toString();
        String ten = edtsuatenprofile.getText().toString();
        String str_sdt = edttsdtprofile.getText().toString();
        String str_diachi = edtdiachi.getText().toString();

        // Date ngaysinh = (Date) edtsuangaysinh.getText();

        if(rdButtonnam.isChecked()){
                    isGioitinh = 1;
        }
        if(rdButtonnu.isChecked()){
                    isGioitinh = 0;
        }
        compositeDisposable.add( apiBanHang.updateprofile( Utils.user_curent.getId(),ten,str_sdt,str_diachi,"",isGioitinh,ngay )
        .subscribeOn( Schedulers.io())
        .observeOn( AndroidSchedulers.mainThread() )
        .subscribe(

                userModel -> {
                    Utils.user_curent.setSdt( str_sdt );
                    Utils.user_curent.setDiachi( str_diachi );
                    Utils.user_curent.setGioitinh( isGioitinh );
                    Utils.user_curent.setUsername( ten );
                    Utils.user_curent.setNgaysinh( ngay );



                    Toast.makeText( getApplicationContext(), "Update succes", Toast.LENGTH_LONG ).show();
                },
                throwable -> {
                    Log.d( "log", throwable.getMessage() );
                }
        ));





    }

    private void initView() {

        btnhuythem = findViewById( R.id.butonhuy );
        btnxacnhanthem = findViewById( R.id.buttonxacnhan );
        btnthemanh = findViewById( R.id.butonthemanh );
        imgthemanhprofile = findViewById( R.id.imgthemanhprofile );
        toolbar = findViewById( R.id.toolbarsuaprofile );
        rdButtonnam = findViewById( R.id.radiobuttongtnam );
        rdButtonnu = findViewById( R.id.radiobuttongtnu );
        edtsuaten = findViewById( R.id.edtsuatenprofile );
        radioGroup = findViewById( R.id.radiogroup );
        buttonsua = findViewById( R.id.btnsuaprofile );
        edtsuadiachi = findViewById( R.id.edtdiachi );
        edtsuasodienthoai = findViewById( R.id.edtngaysinhprofile );
        edtsuangaysinh = findViewById( R.id.edtngaysinhprofile );
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}