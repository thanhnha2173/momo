package com.example.do_an.model;

public class User {
    long id;
    String MaTTCN;
    long Soduvi;
    long Sodutuithantai;

    public User() {}

    public User(long id, String MaTTCN, long Soduvi, long Sodutuithantai) {
        this.id = id;
        this.MaTTCN = MaTTCN;
        this.Soduvi = Soduvi;
        this.Sodutuithantai = Sodutuithantai;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaTTCN() {
        return MaTTCN;
    }

    public void setMaTTCN(String maTTCN) {
        MaTTCN = maTTCN;
    }

    public long getSoduvi() {
        return Soduvi;
    }

    public void setSoduvi(long soduvi) {
        Soduvi = soduvi;
    }

    public long getSodutuithantai() {
        return Sodutuithantai;
    }

    public void setSodutuithantai(long sodutuithantai) {
        Sodutuithantai = sodutuithantai;
    }
}
