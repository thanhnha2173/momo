//package com.example.do_an.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.do_an.database.MyDatabaseHelper;
//import com.example.do_an.model.HisCar;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class CarHisDataSource {
//    private SQLiteDatabase database;
//    private MyDatabaseHelper dbHelper;
//
//    private String[] allColumns = {
//            MyDatabaseHelper.COLUMN_CAR_HIS_ID,
//            MyDatabaseHelper.COLUMN_CAR_HIS_NAMELOCAL,
//            MyDatabaseHelper.COLUMN_CAR_HIS_DATEFIX,
//            MyDatabaseHelper.COLUMN_CAR_HIS_MOTA,
//            MyDatabaseHelper.COLUMN_CAR_HIS_STATUS,
//            MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER
//    };
//
//    public CarHisDataSource(Context context) {
//        dbHelper = new MyDatabaseHelper(context);
//    }
//
//
//    public void open() throws SQLException {
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public void close() {
//        dbHelper.close();
//    }
//
//    public HisCar createHisCar(String nameLocal, String dateFix, String status, String mota, String hisuser) {
//        ContentValues values = new ContentValues();
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_NAMELOCAL, nameLocal);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_DATEFIX, dateFix);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_STATUS, status);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_MOTA, mota);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER, hisuser);
//        long insertId = database.insert(MyDatabaseHelper.TABLE_CAR_HIS, null, values);
//        Cursor cursor = database.query(MyDatabaseHelper.TABLE_CAR_HIS, allColumns,
//                MyDatabaseHelper.COLUMN_CAR_HIS_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        HisCar newHisCar = cursorToHisCar(cursor);
//        cursor.close();
//        return newHisCar;
//    }
//
//    public void updateHisCar(HisCar hisCar, String nameLocal, String dateFix, String status, String mota, String hisuser) {
//        ContentValues values = new ContentValues();
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_NAMELOCAL, nameLocal);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_DATEFIX, dateFix);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_STATUS, status);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_MOTA, mota);
//        values.put(MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER, hisuser);
//        String whereClause = MyDatabaseHelper.COLUMN_CAR_HIS_DATEFIX + " = ? AND " + MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER + " = ?";
//        String[] whereArgs = {String.valueOf(hisCar.getDateFix()), hisCar.getHisuser()};
//        database.update(MyDatabaseHelper.TABLE_CAR_HIS, values, whereClause, whereArgs);
//    }
//
//    public void updateHisCarStatus(HisCar hisCar, String status) {
//            ContentValues values = new ContentValues();
//            values.put(MyDatabaseHelper.COLUMN_CAR_HIS_STATUS, status);
//            String whereClause = MyDatabaseHelper.COLUMN_CAR_HIS_DATEFIX + " = ?";
//            String[] whereArgs = {String.valueOf(hisCar.getDateFix())};
//            database.update(MyDatabaseHelper.TABLE_CAR_HIS, values, whereClause, whereArgs);
//    }
//
//    public void deleteHisCar(HisCar hisCar) {
//        String whereClause = MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER + " = ?";
//        String[] whereArgs = { String.valueOf(hisCar.getHisuser()) };
//        database.delete(MyDatabaseHelper.TABLE_CAR_HIS, whereClause, whereArgs);
//    }
//
//
//    private boolean checkDateTime(String date) {
//        String[] arrOfDate = date.split("/"); // Cắt chuỗi bởi dấu /
//        final Calendar c = Calendar.getInstance();
//        int currentYear = c.get(Calendar.YEAR);
//        int currentMonth = c.get(Calendar.MONTH) + 1;
//        int currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//        int selectedYear = Integer.valueOf(arrOfDate[2]);
//        int selectedMonth = Integer.valueOf(arrOfDate[1]);
//        int selectedDayOfMonth = Integer.valueOf(arrOfDate[0]);
//        if (date.equals(""))
//            return false;
//        if (selectedYear > currentYear ||
//                (selectedYear == currentYear && selectedMonth > currentMonth) ||
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth) ||
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth)) {
//            return true;
//        } else
//            return false;
//    }
//
//    public List<HisCar> getAllHisCar(String username) {
//
//        List<HisCar> hisCars = new ArrayList<HisCar>();
//        Cursor cursor = database.query(MyDatabaseHelper.TABLE_CAR_HIS,
//                allColumns, MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER + " = ?",
//                new String[]{username}, null, null, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            HisCar hisCar = cursorToHisCar(cursor);
//            hisCars.add(hisCar);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return hisCars;
//    }
//
//    public List<HisCar> getAllHisCarAdmin() {
//
//        List<HisCar> hisCars = new ArrayList<HisCar>();
//        Cursor cursor = database.query(MyDatabaseHelper.TABLE_CAR_HIS,
//                allColumns, null, null, null, null, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            HisCar hisCar = cursorToHisCar(cursor);
//            hisCars.add(hisCar);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return hisCars;
//    }
//
////    public void deleteHisCar(String hisuser) {
////        String whereClause = MyDatabaseHelper.COLUMN_CAR_HIS_HISUSER + " = ?";
////        String[] whereArgs = { phone };
////        database.delete(MyDatabaseHelper.TABLE_CAR_INFO, whereClause, whereArgs);
////    }
//
//    private HisCar cursorToHisCar(Cursor cursor) {
//        HisCar hisCar = new HisCar();
//        hisCar.setNameLocal(cursor.getString(1));
//        hisCar.setDateFix(cursor.getString(2));
//        hisCar.setMota(cursor.getString(3));
//        hisCar.setStatus(cursor.getString(4));
//        hisCar.setHisuser(cursor.getString(5));
//        return hisCar;
//    }
//}
//
