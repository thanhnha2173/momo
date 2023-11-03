package com.example.do_an.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.do_an.model.User;
import com.example.do_an.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    // Define the columns for the TaiKhoanTable
    private String[] taiKhoanColumns = {
            MyDatabaseHelper.TaiKhoanTable.SO_TK,
            MyDatabaseHelper.TaiKhoanTable.MA_TTCN,
            MyDatabaseHelper.TaiKhoanTable.SO_DU_VI,
            MyDatabaseHelper.TaiKhoanTable.SO_DU_TUI_THAN_TAI
    };

    // Define the columns for the UserInfoTable
    private String[] userInfoColumns = {
            MyDatabaseHelper.ThongTinCaNhanTable.MA_TTCN,
            MyDatabaseHelper.ThongTinCaNhanTable.HO_TEN,
            MyDatabaseHelper.ThongTinCaNhanTable.EMAIL,
            MyDatabaseHelper.ThongTinCaNhanTable.GIOI_TINH,
            MyDatabaseHelper.ThongTinCaNhanTable.NGAY_SINH,
            MyDatabaseHelper.ThongTinCaNhanTable.CCCD_OR_CMND,
            MyDatabaseHelper.ThongTinCaNhanTable.DIA_CHI,
            MyDatabaseHelper.ThongTinCaNhanTable.MAT_KHAU
    };

    public UserDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User createUser(long SoTK) {
        // Insert values into the TaiKhoanTable
        ContentValues taiKhoanValues = new ContentValues();
        taiKhoanValues.put(MyDatabaseHelper.TaiKhoanTable.SO_TK, SoTK);
        taiKhoanValues.put(MyDatabaseHelper.TaiKhoanTable.MA_TTCN, "CN" + SoTK);
        taiKhoanValues.put(MyDatabaseHelper.TaiKhoanTable.SO_DU_VI, 0); // Set initial value for SO_DU_VI
        taiKhoanValues.put(MyDatabaseHelper.TaiKhoanTable.SO_DU_TUI_THAN_TAI, 0); // Set initial value for SO_DU_TUI_THAN_TAI
        long taiKhoanInsertId = database.insert(MyDatabaseHelper.TaiKhoanTable.TABLE_NAME, null, taiKhoanValues);

        // Create a new User object with the provided SoTK and "CN" + SoTK as MaTTCN
        User newUser = new User();
        newUser.setId(SoTK);
        newUser.setMaTTCN("CN" + SoTK);
        newUser.setSoduvi(0); // Set initial value for SO_DU_VI
        newUser.setSodutuithantai(0); // Set initial value for SO_DU_TUI_THAN_TAI
        return newUser;
    }

    // Rest of the methods remain the same...


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Cursor cursor = database.query(MyDatabaseHelper.TaiKhoanTable.TABLE_NAME,
                taiKhoanColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();

        return users;
    }
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setMaTTCN(cursor.getString(1));
        user.setSoduvi(cursor.getLong(2));
        user.setSodutuithantai(cursor.getLong(3));
        return user;
    }
}
