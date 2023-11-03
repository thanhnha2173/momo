package com.example.do_an.model;

public class LSGD {
    String idNT;
    String idCT;
    String idRT;
    long tienNap;
    long tienChuyen;
    long tienRut;
    long sdCT;
    String nd;

    public LSGD() {}

    public LSGD(String idCT, long tienNap) {
        this.idNT = idNT;
        this.tienNap = tienNap;
    }
    public LSGD(String idCT, long tienChuyen, String nd, long soDu) {
        this.idCT = idCT;
        this.tienChuyen = tienChuyen;
        this.nd = nd;
        this.sdCT = soDu;
    }
    public LSGD(String idRT, long tienRut, String nd) {
        this.idRT = idRT;
        this.tienRut = tienRut;
        this.nd = nd;
    }

    public String getIdNT() {
        return idNT;
    }

    public void setIdNT(String idNT) {
        this.idNT = idNT;
    }

    public String getIdCT() {
        return idCT;
    }

    public void setIdCT(String idCT) {
        this.idCT = idCT;
    }

    public String getIdRT() {
        return idRT;
    }

    public void setIdRT(String idRT) {
        this.idRT = idRT;
    }

    public long getTienNap() {
        return tienNap;
    }

    public void setTienNap(long tienNap) {
        this.tienNap = tienNap;
    }

    public long getTienChuyen() {
        return tienChuyen;
    }

    public void setTienChuyen(long tienChuyen) {
        this.tienChuyen = tienChuyen;
    }

    public long getTienRut() {
        return tienRut;
    }

    public void setTienRut(long tienRut) {
        this.tienRut = tienRut;
    }

    public long getSdCT() {
        return sdCT;
    }

    public void setSdCT(long sdCT) {
        this.sdCT = sdCT;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }
}
