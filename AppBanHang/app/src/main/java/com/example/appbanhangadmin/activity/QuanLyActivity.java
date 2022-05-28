package com.example.appbanhangadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.SanPhamAdminAdapter;
import com.example.appbanhangadmin.adapter.SanPhamMoiAdapter;
import com.example.appbanhangadmin.model.EventBus.SuaXoaEvent;
import com.example.appbanhangadmin.model.SanPhamMoi;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphCardView;

public class QuanLyActivity extends AppCompatActivity {
    ImageView img_them;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ApiBanHang apiBanHang;
    List<SanPhamMoi> list;
    SanPhamAdminAdapter adapter;
    SanPhamMoi sanPhamSuaXoa;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quan_ly );
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        initView();
        initControl();
        getSanPhamMoi();
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    private void initControl() {
        img_them.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSanPhamActivity.class);
                startActivity( intent );
            }
        } );
    }
    private void getSanPhamMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn( Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                list = sanPhamMoiModel.getResult();
                                adapter = new SanPhamAdminAdapter(getApplicationContext(),list);
                                recyclerView.setAdapter(adapter);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"không kết nối được với server"+ throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void initView() {
        img_them = findViewById( R.id.imgthemsanpham );
        toolbar = findViewById( R.id.toolbar_quanli_thoitrangnam );
        recyclerView = findViewById( R.id.recycleview_quanly_thoitrangnam );
        LinearLayoutManager linearLayoutManager = new GridLayoutManager( this,2 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( linearLayoutManager );
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals( "Sửa" )){
            suaSanPham();
        }
        else if(item.getTitle().equals( "Xóa" )){
            xoaSanPham();
        }
        return super.onContextItemSelected( item );
    }

    private void xoaSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder( recyclerView.getRootView().getContext() );
        builder.setTitle( "Thông báo" );
        builder.setMessage( "Bạn có muốn xóa sản phẩm này");
        builder.setPositiveButton( "Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compositeDisposable.add( apiBanHang.xoasanpham( sanPhamSuaXoa.getId())
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .subscribe(
                                sanPhamMoiModel -> {
                                    if(sanPhamMoiModel.isSuccess()){
                                        Toast.makeText( getApplicationContext(),sanPhamMoiModel.getMessage(),Toast.LENGTH_LONG ).show();
                                        getSanPhamMoi();
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

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(),ChinhSuaThongTinSanPhamActivity.class);
        intent.putExtra( "sua",sanPhamSuaXoa );
        startActivity( intent );
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaEvent event){
        if(event != null){
            sanPhamSuaXoa = event.getSanPhamMoi();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register( this );
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister( this );
    }
}