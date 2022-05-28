package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.ThoiTrangNamAdapter;
import com.example.appbanhangadmin.model.SanPhamMoi;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText editsearch;
    ThoiTrangNamAdapter adapterttnam;
    List<SanPhamMoi> sanPhamMoiList;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );
        initView();
        ActionToolBar();
    }

    private void initView() {
        sanPhamMoiList = new ArrayList<>();
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL).create( ApiBanHang.class );
        editsearch = findViewById( R.id.edit_search );
        toolbar = findViewById( R.id.toolbarsearch);
        recyclerView = findViewById( R.id.recycleviewsearch );

        LinearLayoutManager linearLayoutManager = new GridLayoutManager( this,2 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( linearLayoutManager );

        editsearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    sanPhamMoiList.clear();
                    adapterttnam = new ThoiTrangNamAdapter( getApplicationContext(),sanPhamMoiList );
                    recyclerView.setAdapter( adapterttnam );
                }else{
                    getDataSearch(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }

    private void getDataSearch(String s) {
        sanPhamMoiList.clear();

        compositeDisposable.add( apiBanHang.search( s )
        .subscribeOn( Schedulers.io() )
        .observeOn( AndroidSchedulers.mainThread() )
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        sanPhamMoiList = sanPhamMoiModel.getResult();
                        adapterttnam = new ThoiTrangNamAdapter( getApplicationContext(),sanPhamMoiList );
                        recyclerView.setAdapter( adapterttnam );
                    }
                },
                throwable -> {
                    Toast.makeText( getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT ).show();
                }
        ));
    }

    private void ActionToolBar() {
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}