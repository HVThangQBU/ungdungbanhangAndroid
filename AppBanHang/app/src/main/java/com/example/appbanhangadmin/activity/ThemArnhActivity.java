package com.example.appbanhangadmin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appbanhang.R;
import com.example.appbanhangadmin.model.MessageModel;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemArnhActivity extends AppCompatActivity {
    AppCompatButton btnxacnhanthem,btnhuythem,btnthemanh;
    ImageView imgthemanhprofile;
    Toolbar toolbar;
    String mediaPath;
    EditText edtsuaten,themhinhanh,edtsuangaysinh,edtsuasodienthoai,edtsuadiachi;
    RadioGroup radioGroup;
    RadioButton rdButtonnu,rdButtonnam;
    AppCompatButton buttonsua;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    int REQUEST_CODE_FOLDER = 456;
    int isGioitinh = 0;
   boolean flag = false;
    String pathImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_them_arnh );
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        Paper.init( this );
        initView();
        initControl();
        getDaTaUser();

    }

    private void initControl() {
        btnthemanh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ThemArnhActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK){
            mediaPath = data.getDataString();
            Log.d("duongdan","onActivityResult"+mediaPath);
            Uri uri1 = Uri.parse( mediaPath );
            imgthemanhprofile.setImageURI( uri1 );
            uploadMultipleFiles();
            flag = true;

        }


    }
    private void getDaTaUser(){
        EditText edtsuatenprofile = findViewById( R.id.edtsuatenprofile );
        EditText edtngaysinhprofile =  findViewById( R.id.edtngaysinhprofile );
        EditText edttsdtprofile =  findViewById( R.id.edttsdtprofile );
        EditText edtdiachi =  findViewById( R.id.edtdiachi );
        themhinhanh = findViewById( R.id.themhinhanh );
        edtsuatenprofile.setText( Utils.user_curent.getUsername() );
        edttsdtprofile.setText( Utils.user_curent.getSdt() );
        edtdiachi.setText( Utils.user_curent.getDiachi() );
        edtngaysinhprofile.setText( Utils.user_curent.getNgaysinh() );
        if(Utils.user_curent.getHinhanh().contains( "https" )){
            Glide.with(getApplicationContext()).load(Utils.user_curent.getHinhanh()).into(imgthemanhprofile);
        }else{
            String hinh = Utils.BASE_URL+"imagesUser/"+Utils.user_curent.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).diskCacheStrategy(  DiskCacheStrategy.NONE).skipMemoryCache( true ).into(imgthemanhprofile);
        }


    }
    private void updateInfo() {
        EditText edtsuatenprofile = findViewById( R.id.edtsuatenprofile );
        EditText edtngaysinhprofile =  findViewById( R.id.edtngaysinhprofile );
        EditText edttsdtprofile =  findViewById( R.id.edttsdtprofile );
        EditText edtdiachi =  findViewById( R.id.edtdiachi );
        themhinhanh = findViewById( R.id.themhinhanh );
        String ngay = edtngaysinhprofile.getText().toString();
        String ten = edtsuatenprofile.getText().toString();
        String str_sdt = edttsdtprofile.getText().toString();
        String str_diachi = edtdiachi.getText().toString();
        String str_hinhanh = themhinhanh.getText().toString().trim();
        if(rdButtonnam.isChecked()){
                    isGioitinh = 1;
        }
        if(rdButtonnu.isChecked()){
                    isGioitinh = 0;
        }
        if (flag==false){
            pathImage = Utils.user_curent.getHinhanh();
        }

        compositeDisposable.add( apiBanHang.updateprofile( Utils.user_curent.getId(),ten,str_sdt,str_diachi,pathImage,isGioitinh,ngay )
        .subscribeOn( Schedulers.io())
        .observeOn( AndroidSchedulers.mainThread() )
        .subscribe(

                userModel -> {
                    Utils.user_curent.setSdt( str_sdt );
                    Utils.user_curent.setDiachi( str_diachi );
                    Utils.user_curent.setGioitinh( isGioitinh );
                    Utils.user_curent.setUsername( ten );
                    Utils.user_curent.setNgaysinh( ngay );
                    Utils.user_curent.setHinhanh( pathImage );
                    Paper.book().write( "user",Utils.user_curent );

                    Toast.makeText( getApplicationContext(), "Update succes", Toast.LENGTH_LONG ).show();
                },
                throwable -> {
                    Log.d( "log", throwable.getMessage() );
                }
        ));
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query( uri,null,null,null,null );
        if(cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
            result = cursor.getString( index );
            cursor.close();
        }
        return  result;
    }
    private void uploadMultipleFiles() {
        Uri uri = Uri.parse( mediaPath );
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(getPath( uri ));
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create( MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
//        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<MessageModel> call = apiBanHang.uploadfileuser( fileToUpload1 );
        call.enqueue(new Callback< MessageModel >() {
            @Override
            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        themhinhanh.setText( serverResponse.getName() );
                        pathImage = serverResponse.getName();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }
            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("log",t.getMessage());
            }
        });
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

        if (Utils.user_curent.getGioitinh() == 0){
            Log.d( "log", Utils.user_curent.getGioitinh()+ " 33333" );
            rdButtonnu.setChecked( true );
        }else {
            rdButtonnam.setChecked( true );
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}