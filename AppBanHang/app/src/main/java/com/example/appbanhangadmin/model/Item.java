package com.example.appbanhangadmin.model;

public class Item {
    int idsanpham;
    String tensanpham;
    int soluong;
    String hinhanhsanpham;
    Long giasanpham;

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public Long getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Long giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }
}
