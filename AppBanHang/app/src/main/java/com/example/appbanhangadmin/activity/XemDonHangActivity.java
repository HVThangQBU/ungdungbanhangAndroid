package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.DonHangAdapter;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerViewredonhang;
    Toolbar toolbarxemdonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_xem_don_hang );
        initView();
        initToolBar();
        getOrder();
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
    //    Log.d( "te", Utils.user_curent.getId() + "" );

    }

    private void getOrder() {
        compositeDisposable.add(apiBanHang.xemDonHang( Utils.user_curent.getId() )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter( getApplicationContext(),donHangModel.getResult() );
                            recyclerViewredonhang.setAdapter( adapter );
                      //      Toast.makeText( getApplicationContext(),donHangModel.getResult().get(0).getItem().get(0).getTensanpham(),Toast.LENGTH_SHORT ).show();
                        },
                        throwable -> {
                            Log.d( "te", throwable.getMessage() );
                        }
                ));
    }

    private void initToolBar() {
        setSupportActionBar( toolbarxemdonhang );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true ) ;
        toolbarxemdonhang.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL ).create( ApiBanHang.class );
        recyclerViewredonhang = findViewById( R.id.recycleviewxemdonhang );
        toolbarxemdonhang = findViewById( R.id.toolbarxemdonhang );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerViewredonhang.setLayoutManager( layoutManager );

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}