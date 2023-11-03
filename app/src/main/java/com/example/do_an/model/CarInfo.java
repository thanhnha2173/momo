package com.example.do_an.model;

public class CarInfo {
    String namecar;
    String bienso;
    String dtxl;
    String caruser;

    public CarInfo() {
    }

    public CarInfo(String namecar, String bienso, String dtxl, String caruser) {
        this.namecar = namecar;
        this.bienso = bienso;
        this.dtxl = dtxl;
        this.caruser = caruser;
    }

    public String getNamecar() {
        return namecar;
    }

    public void setNamecar(String namecar) {
        this.namecar = namecar;
    }

    public String getBienso() {
        return bienso;
    }

    public void setBienso(String bienso) {
        this.bienso = bienso;
    }

    public String getDtxl() {
        return dtxl;
    }

    public void setDtxl(String dtxl) {
        this.dtxl = dtxl;
    }

    public String getCaruser() {
        return caruser;
    }

    public void setCaruser(String caruser) {
        this.caruser = caruser;
    }
}
