package com.example.appbanhang.retrofit;

import com.example.appbanhang.model.DonHangModel;
import com.example.appbanhang.model.LoaiSpModel;
import com.example.appbanhang.model.SanPhamMoiModel;
import com.example.appbanhang.model.UserModel;


import java.util.Date;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    // GET DATA
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
    //POST DATA
    @POST("chitietsp.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("idsanpham") int idsanpham

    );
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangki(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("sdt") String sdt,
            @Field("diachi") String diachi


    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
            @Field("email") String email,
            @Field("pass") String pass


    );
    @POST("resetpass.php")
    @FormUrlEncoded
    Observable<UserModel> resetpass(
            @Field("email") String email

    );
    @POST("donghang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id

    );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search

    );
    @POST("updateprofile.php")
    @FormUrlEncoded
    Observable<UserModel> updateprofile(
            @Field("id") int  id,
            @Field("username") String username,
            @Field("sdt") String sdt,
            @Field("diachi") String diachi,
            @Field("hinhanh") String hinhanh,
            @Field("gioitinh") int gioitinh,
            @Field("ngaysinh") String ngaysinh

    );
}
