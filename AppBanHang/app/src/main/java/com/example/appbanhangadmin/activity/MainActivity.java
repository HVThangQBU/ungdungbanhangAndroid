package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhangadmin.adapter.LoaiSpAdapter;
import com.example.appbanhangadmin.adapter.SanPhamMoiAdapter;
import com.example.appbanhangadmin.model.Loaisp;
import com.example.appbanhangadmin.model.SanPhamMoi;
import com.example.appbanhangadmin.model.User;
import com.example.appbanhangadmin.retrofit.ApiBanHang;
import com.example.appbanhangadmin.retrofit.RetrofitClient;
import com.example.appbanhangadmin.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<Loaisp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    SanPhamMoiAdapter spAdapter;
    List<SanPhamMoi> mangSpMoi;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance( Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init( this );
        if(Paper.book().read("user") != null){
            User user = Paper.book().read( "user" );
            Utils.user_curent = user;
        }
        AnhXa();
        ActionBar();

        if (isConnected(this)){
//            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getLoaiSanPham();
            getSanPhamMoi();
            setAdmin();
            getEvenClick();
        }else{
            Toast.makeText(getApplicationContext(),"khong co internet vui long ket noi",Toast.LENGTH_LONG).show();
        }
    }

    private void getEvenClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent thoitrangnam = new Intent(getApplicationContext(),ThoiTrangNamActivity.class);
                        thoitrangnam.putExtra("idsanpham",1);
                        startActivity(thoitrangnam);
                        break;
                    case 2:
                        Intent thoitrangnu = new Intent(getApplicationContext(),ThoiTrangNuActivity.class);
                        thoitrangnu.putExtra("idsanpham",2);
                        startActivity(thoitrangnu);
                        break;
                    case 3:
                        Intent giaydepnam = new Intent(getApplicationContext(),GiayDepNamActivity.class);
                        giaydepnam.putExtra("idsanpham",3);
                        startActivity(giaydepnam);
                        break;
                    case 4:
                        Intent giaydepnu = new Intent(getApplicationContext(),GiayDepNuACtivity.class);
                        giaydepnu.putExtra("idsanpham",4);
                        startActivity(giaydepnu);
                        break;
                    case 5:
                        Intent lienhe = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 6:
                       Intent thongtin = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(thongtin);
                        break;
                    case 7:
                        Intent donhang = new Intent(getApplicationContext(),XemDonHangActivity.class);
                        startActivity(donhang);
                        break;
//                    case 8:
//                        // xoa key user
//                        Paper.book().delete( "user" );
//                        Intent dangnhap = new Intent(getApplicationContext(),DangNhapActivity.class);
//                        startActivity(dangnhap);
//                        finish();
//                        break;
                    case 8:
                        Intent profile = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(profile);
                        break;
                    case 9:
                        Intent quanly = new Intent(getApplicationContext(),QuanLyActivity.class);
                        startActivity(quanly);
                        break;


                }
            }
        });
    }
    // them set admin
private void setAdmin(){
        compositeDisposable.add(apiBanHang.setadmin( Utils.user_curent.getQuyen())
        .subscribeOn( Schedulers.io() )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                userModel -> {
                        Utils.user_curent.setQuyen( Utils.user_curent.getQuyen() );

                },throwable -> {
                    Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                }
        ));
}
    private void getSanPhamMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"không kết nối được với server"+ throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    loaiSpModel -> {
                        if (loaiSpModel.isSuccess()){
                            mangloaisp = loaiSpModel.getResult();
                            if(Utils.user_curent.getQuyen() == 0) {
                                mangloaisp.add( new Loaisp( "Tài khoản", "https://cdn-icons-png.flaticon.com/512/1177/1177568.png?w=826" ) );
                            }
                            else{
                                mangloaisp.add( new Loaisp( "Tài khoản", "https://cdn-icons-png.flaticon.com/512/1177/1177568.png?w=826" ) );
                                mangloaisp.add( new Loaisp( "Quản lý","https://img.icons8.com/dusk/344/edit--v1.png"));
                            }

                            loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                            listViewManHinhChinh.setAdapter(loaiSpAdapter);

                        }
                    }
                ));
    }


    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cf.shopee.vn/file/d5c8089ce03276d6d4f81a315659e12d");
        mangquangcao.add("https://cf.shopee.vn/file/5e2aea4e7332a681b013e0bd4eae3aa1");
        mangquangcao.add("https://cf.shopee.vn/file/4b39b86b4f8c6869635196856c2d2983");
        // do du lieu cac link anh vao ImageView
        for(int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
//        // view tu run
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById( R.id.menu_soluong );
        frameLayout = findViewById( R.id.framegiohangchitiet );
        imgsearch = findViewById( R.id.img_search );
        //khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();

        }else{

            int totalItem = 0;

            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf( totalItem));
        }
        frameLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity( giohang );
            }
        } );
        imgsearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity( intent );
            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();

        int totalItem = 0;

        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf( totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }

    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }


}