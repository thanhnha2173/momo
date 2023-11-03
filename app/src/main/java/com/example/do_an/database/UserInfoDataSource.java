package com.example.do_an.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.do_an.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDataSource {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    private String[] allColumns = {
            MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN,
            MyDatabaseHelper.ThongTinCaNhanTable.HO_TEN,
            MyDatabaseHelper.ThongTinCaNhanTable.EMAIL,
            MyDatabaseHelper.ThongTinCaNhanTable.GIOI_TINH,
            MyDatabaseHelper.ThongTinCaNhanTable.NGAY_SINH,
            MyDatabaseHelper.ThongTinCaNhanTable.CCCD_OR_CMND,
            MyDatabaseHelper.ThongTinCaNhanTable.DIA_CHI,
            MyDatabaseHelper.ThongTinCaNhanTable.MAT_KHAU
    };

    public UserInfoDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserInfo createUserInfo(String MaTTCN, String HoTen, String Email, String GioiTinh, String NgaySinh, String cccd, String diaChi, int matkhau) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN, MaTTCN);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.HO_TEN, HoTen);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.EMAIL, Email);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.GIOI_TINH, GioiTinh);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.NGAY_SINH, NgaySinh);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.CCCD_OR_CMND, cccd);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.DIA_CHI, diaChi);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.MAT_KHAU, matkhau);

        long insertId = database.insert(MyDatabaseHelper.ThongTinCaNhanTable.TABLE_NAME, null, values);

        if (insertId != -1) {
            Cursor cursor = database.query(MyDatabaseHelper.ThongTinCaNhanTable.TABLE_NAME,
                    allColumns, MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            UserInfo newUserInfo = cursorToUserInfo(cursor);
            cursor.close();
            return newUserInfo;
        } else {
            // Handle the case when the insert is not successful (optional, depending on your app's logic)
            return null;
        }
    }


    public void updateUserInfo(UserInfo userInfo, String maTTCN, String hoTen, String email, String gioiTinh, String ngaySinh, String CCCD, String diaChi, int matKhau) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN, maTTCN);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.HO_TEN, hoTen);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.EMAIL, email);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.GIOI_TINH, gioiTinh);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.NGAY_SINH, ngaySinh);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.CCCD_OR_CMND, CCCD);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.DIA_CHI, diaChi);
        values.put(MyDatabaseHelper.ThongTinCaNhanTable.MAT_KHAU, matKhau);

        String whereClause = MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN + " = ?";
        String[] whereArgs = { maTTCN };
        database.update(MyDatabaseHelper.ThongTinCaNhanTable.TABLE_NAME, values, whereClause, whereArgs);
    }

    public List<UserInfo> getAllUserInfos() {
        List<UserInfo> userInfos = new ArrayList<>();
        Cursor cursor = database.query(MyDatabaseHelper.ThongTinCaNhanTable.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserInfo userInfo = cursorToUserInfo(cursor);
            userInfos.add(userInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return userInfos;
    }

    private UserInfo cursorToUserInfo(Cursor cursor) {
        UserInfo userInfo = new UserInfo();
        userInfo.setMaTTCN(cursor.getString(0));
        userInfo.setHoTen(cursor.getString(1));
        userInfo.setEmail(cursor.getString(2));
        userInfo.setGioiTinh(cursor.getString(3));
        userInfo.setNgaySinh(cursor.getString(4));
        userInfo.setCCCD(cursor.getString(5));
        userInfo.setDiaChi(cursor.getString(6));
        userInfo.setMatKhau(cursor.getInt(7));
        return userInfo;
    }
}
