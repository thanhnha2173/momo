package com.example.do_an.model;

public class AccountModel {
    private String CCCD;
    private String DiaChi;
    private String Email;
    private String GioiTinh;

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getMaTTCN() {
        return MaTTCN;
    }

    public void setMaTTCN(String maTTCN) {
        MaTTCN = maTTCN;
    }

    public String getMatkhau() {
        return Matkhau;
    }

    public void setMatkhau(String matkhau) {
        Matkhau = matkhau;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    private String HoTen;
    private String MaTTCN;
    private String Matkhau;
    private String NgaySinh;

    public AccountModel() {
        // Empty constructor needed for Firestore
    }
}
