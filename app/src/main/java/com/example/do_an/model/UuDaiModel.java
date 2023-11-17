package com.example.do_an.model;

public class UuDaiModel {
    private String tenUuDai;
    private String thongTinChiTiet;
    private String imageUrl;

    public UuDaiModel(String tenUuDai, String thongTinChiTiet, String imageUrl) {
        this.tenUuDai = tenUuDai;
        this.thongTinChiTiet = thongTinChiTiet;
        this.imageUrl = imageUrl; // Gán URL hình ảnh khi tạo đối tượng
    }

    public String getTenUuDai() {
        return tenUuDai;
    }

    public void setTenUuDai(String tenUuDai) {
        this.tenUuDai = tenUuDai;
    }

    public String getThongTinChiTiet() {
        return thongTinChiTiet;
    }

    public void setThongTinChiTiet(String thongTinChiTiet) {
        this.thongTinChiTiet = thongTinChiTiet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
