package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.SanPhamAdminAdapter;
import com.example.appbanhangadmin.model.SanPhamMoi;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;


import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietAdminActivity  extends AppCompatActivity {
    TextView tensp,giasp,motasp;
    Button btnsua,btnxoa;
    ImageView imghinhanh;
    SanPhamAdminAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<SanPhamMoi> list;
    ApiBanHang apiBanHang;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_admin );
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        initView();
        ActionToolBar();
        initData();

    }


    private void muaGioHang(){

        Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
        startActivity(giohang);
    }


    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra( "chitiet" );
        tensp.setText( sanPhamMoi.getTensanpham() );
        motasp.setText( sanPhamMoi.getMotasanpham() );
        if (sanPhamMoi.getHinhanhsanpham().contains( "https" )) {
            Glide.with( getApplicationContext() ).load( sanPhamMoi.getHinhanhsanpham() ).into( imghinhanh );
        } else {
            String hinh = Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanhsanpham();
            Glide.with( getApplicationContext() ).load( hinh ).into( imghinhanh );
        }

        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
        giasp.setText( "Giá: " + decimalFormat.format( Double.parseDouble( sanPhamMoi.getGiasanpham() ) ) + "Đ" );
//        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
//        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
//        spinner.setAdapter( adapterspin );
        btnsua.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChinhSuaThongTinSanPhamActivity.class);
                intent.putExtra( "sua",sanPhamMoi );
                startActivity( intent );
            }
        } );
        btnxoa.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder( view.getRootView().getContext() );
                builder.setTitle( "Thông báo" );
                builder.setMessage( "Bạn có muốn xóa sản phẩm này");
                builder.setPositiveButton( "Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        compositeDisposable.add( apiBanHang.xoasanpham( sanPhamMoi.getId())
                                .subscribeOn( Schedulers.io() )
                                .observeOn( AndroidSchedulers.mainThread() )
                                .subscribe(
                                        sanPhamMoiModel -> {
                                            if(sanPhamMoiModel.isSuccess()){
                                                Toast.makeText( getApplicationContext(),sanPhamMoiModel.getMessage(),Toast.LENGTH_LONG ).show();

                                            }
                                            else {
                                                Toast.makeText( getApplicationContext(),sanPhamMoiModel.getMessage(),Toast.LENGTH_LONG ).show();
                                            }
                                        },
                                        throwable -> {
                                            Toast.makeText( getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG ).show();
                                        }
                                ));
                        Intent intent = new Intent(getApplicationContext(),QuanLyActivity.class);
                        startActivity( intent );
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
        } );

    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        tensp = findViewById( R.id.txttensannpham );
        giasp = findViewById( R.id.txtgiasanpham );
        motasp = findViewById( R.id.txtmotachitiet );
        btnsua = findViewById( R.id.btnsuasanpham );
        btnxoa = findViewById( R.id.btnhuychitietxoasanpham );
        imghinhanh = findViewById( R.id.imgchitietsanpham );
        toolbar = findViewById( R.id.toolbarchitietsanpham );
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}