package com.example.do_an.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuCollection implements Parcelable {
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
    protected MenuCollection(Parcel in) {
        id_menuCollection = in.readInt();
        title_menuCollection = in.readString();
        img_menu_Collection = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_menuCollection);
        dest.writeString(title_menuCollection);
        dest.writeInt(img_menu_Collection);
    }
    public static final Creator<MenuCollection> CREATOR = new Creator<MenuCollection>() {
        @Override
        public MenuCollection createFromParcel(Parcel in) {
            return new MenuCollection(in);
        }

        @Override
        public MenuCollection[] newArray(int size) {
            return new MenuCollection[size];
        }
    };

    public int getId_menuCollection() {
        return id_menuCollection;
    }

    public String getTitle_menuCollection() {
        return title_menuCollection;
    }

}
