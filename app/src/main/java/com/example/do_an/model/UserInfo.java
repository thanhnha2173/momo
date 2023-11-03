package com.example.do_an.model;

public class UserInfo {
    String MaTTCN;
    String HoTen;
    String Email;
    String GioiTinh;
    String NgaySinh;
    String CCCD;
    String DiaChi;
    long MatKhau;

    public UserInfo(String maTTCN, String hoTen, String email, String gioiTinh, String ngaySinh, String CCCD, String diaChi, long matKhau) {
        MaTTCN = maTTCN;
        HoTen = hoTen;
        Email = email;
        GioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        this.CCCD = CCCD;
        DiaChi = diaChi;
        MatKhau = matKhau;
    }
    public UserInfo(String maTTCN, String hoTen, String gioiTinh, String ngaySinh, String CCCD) {
        MaTTCN = maTTCN;
        HoTen = hoTen;
        GioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        this.CCCD = CCCD;
    }
    public UserInfo(String maTTCN, String email, String diaChi) {
        MaTTCN = maTTCN;
        Email = email;
        DiaChi = diaChi;
    }
    public UserInfo(String maTTCN, long matKhau) {
        MaTTCN = maTTCN;
        MatKhau = matKhau;

    }

    public UserInfo() {
    }

    public String getMaTTCN() {
        return MaTTCN;
    }

    public void setMaTTCN(String maTTCN) {
        MaTTCN = maTTCN;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
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

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

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

    public long getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(long matKhau) {
        MatKhau = matKhau;
    }
}