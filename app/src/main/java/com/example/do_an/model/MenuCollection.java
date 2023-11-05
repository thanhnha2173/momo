package com.example.do_an.model;

public class MenuCollection {
    private int id_menuCollection;
    private String title_menuCollection;
    private int img_menu_Collection;

    public void setId_menuCollection(int id_menuCollection) {
        this.id_menuCollection = id_menuCollection;
    }

    public void setTitle_menuCollection(String title_menuCollection) {
        this.title_menuCollection = title_menuCollection;
    }

    public int getImg_menu_Collection() {
        return img_menu_Collection;
    }

    public void setImg_menu_Collection(int img_menu_Collection) {
        this.img_menu_Collection = img_menu_Collection;
    }

    public MenuCollection(int id_menuCollection, String title_menuCollection, int img_menu_Collection) {
        this.id_menuCollection = id_menuCollection;
        this.title_menuCollection = title_menuCollection;
        this.img_menu_Collection = img_menu_Collection;
    }

    public int getId_menuCollection() {
        return id_menuCollection;
    }

    public String getTitle_menuCollection() {
        return title_menuCollection;
    }

}
