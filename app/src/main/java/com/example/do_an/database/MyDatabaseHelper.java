package com.example.do_an.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 23; // Tăng phiên bản cơ sở dữ liệu lên 2

    public class TaiKhoanTable {
        public static final String TABLE_NAME = "tai_khoan";
        public static final String SO_TK = "so_tk";
        public static final String MA_TTCN = "ma_ttcn";
        public static final String SO_DU_VI = "so_du_vi";
        public static final String SO_DU_TUI_THAN_TAI = "so_du_tui_than_tai";
    }



    public class ThongTinCaNhanTable {
        public static final String TABLE_NAME = "thong_tin_ca_nhan";
        public static final String MA_TTCN = "ma_ttcn";
        public static final String HO_TEN = "ho_ten";
        public static final String EMAIL = "email";
        public static final String GIOI_TINH = "gioi_tinh";
        public static final String NGAY_SINH = "ngay_sinh";
        public static final String CCCD_OR_CMND = "cccd_or_cmnd";
        public static final String DIA_CHI = "dia_chi";
        public static final String MAT_KHAU = "mat_khau";
    }



    // Câu lệnh tạo bảng user_info
    private String createTaiKhoanTable() {
        return "CREATE TABLE " + TaiKhoanTable.TABLE_NAME + " (" +
                TaiKhoanTable.SO_TK + " INTEGER PRIMARY KEY, " +
                TaiKhoanTable.MA_TTCN + " INTEGER NOT NULL, " +
                TaiKhoanTable.SO_DU_VI + " INTEGER, " +
                TaiKhoanTable.SO_DU_TUI_THAN_TAI + " INTEGER, " +
                "FOREIGN KEY(" + TaiKhoanTable.MA_TTCN + ") REFERENCES " +
                ThongTinCaNhanTable.TABLE_NAME + "(" + ThongTinCaNhanTable.MA_TTCN + "))";
    }


    private String createThongTinCaNhanTable() {
        return "CREATE TABLE " + ThongTinCaNhanTable.TABLE_NAME + " (" +
                ThongTinCaNhanTable.MA_TTCN + " text PRIMARY KEY, " +
                ThongTinCaNhanTable.HO_TEN + " text, " +
                ThongTinCaNhanTable.EMAIL + " text, " +
                ThongTinCaNhanTable.GIOI_TINH + " text, " +
                ThongTinCaNhanTable.NGAY_SINH + " DATE, " +
                ThongTinCaNhanTable.CCCD_OR_CMND + " INT, " +
                ThongTinCaNhanTable.DIA_CHI + " text, " +
                ThongTinCaNhanTable.MAT_KHAU + " INTEGER NOT NULL)";
    }



    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users
        db.execSQL(createTaiKhoanTable());
        db.execSQL(createThongTinCaNhanTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + ThongTinCaNhanTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TaiKhoanTable.TABLE_NAME);
        onCreate(db);
    }
}


