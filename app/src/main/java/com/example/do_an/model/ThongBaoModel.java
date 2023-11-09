package com.example.do_an.model;

public class ThongBaoModel {
    private String title;
    private String price;
    private String date;
    private String hour;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ThongBaoModel(String title, String price, String date, String hour) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.hour = hour;
    }
}
