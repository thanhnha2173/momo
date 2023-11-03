//package com.example.do_an.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.example.do_an.database.MyDatabaseHelper;
//import com.example.do_an.model.CarInfo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CarDataSource {
//    private SQLiteDatabase database;
//    private MyDatabaseHelper dbHelper;
//
//    private String[] allColumns = {MyDatabaseHelper.COLUMN_CAR_ID,
//            MyDatabaseHelper.COLUMN_CAR_NAME,MyDatabaseHelper.COLUMN_CAR_BIENSO,  MyDatabaseHelper.COLUMN_CAR_DTXL, MyDatabaseHelper.COLUMN_CAR_USER};
//
//    public CarDataSource(Context context) {
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
//    public CarInfo createCar(String namecar,String bienso, String dtxl, String caruser) {
//        ContentValues values = new ContentValues();
//        values.put(MyDatabaseHelper.COLUMN_CAR_NAME, namecar);
//        values.put(MyDatabaseHelper.COLUMN_CAR_BIENSO, bienso);
//        values.put(MyDatabaseHelper.COLUMN_CAR_DTXL, dtxl);
//        values.put(MyDatabaseHelper.COLUMN_CAR_USER, caruser);
//        long insertId = database.insert(MyDatabaseHelper.TABLE_CAR_INFO, null,
//                values);
//        Cursor cursor = database.query(MyDatabaseHelper.TABLE_CAR_INFO,
//                allColumns, MyDatabaseHelper.COLUMN_CAR_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        CarInfo newcar = cursorToCarInfo(cursor);
//        cursor.close();
//        return newcar;
//    }
//
//    public void updateCar(CarInfo carInfo, String namecar, String bienso, String dtxl, String caruser) {
//        ContentValues values = new ContentValues();
//        values.put(MyDatabaseHelper.COLUMN_CAR_NAME, namecar);
//        values.put(MyDatabaseHelper.COLUMN_CAR_BIENSO, bienso);
//        values.put(MyDatabaseHelper.COLUMN_CAR_DTXL, dtxl);
//        values.put(MyDatabaseHelper.COLUMN_CAR_USER, caruser);
//        String whereClause = MyDatabaseHelper.COLUMN_CAR_USER + " = ?";
//        String[] whereArgs = { String.valueOf(carInfo.getCaruser()) };
//        database.update(MyDatabaseHelper.TABLE_CAR_INFO, values, whereClause, whereArgs);
//    }
//
//    public List<CarInfo> getAllCar() {
//        List<CarInfo> carInfos = new ArrayList<CarInfo>();
//        Cursor cursor = database.query(MyDatabaseHelper.TABLE_CAR_INFO,
//                allColumns, null, null, null, null, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            CarInfo carInfo = cursorToCarInfo(cursor);
//            carInfos.add(carInfo);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return carInfos;
//    }
//    public void deleteCar(String phone) {
//        String whereClause = MyDatabaseHelper.COLUMN_CAR_USER + " = ?";
//        String[] whereArgs = { phone };
//        database.delete(MyDatabaseHelper.TABLE_CAR_INFO, whereClause, whereArgs);
//        Log.d("delCar", "Ok");
//
//    }
//
//    private CarInfo cursorToCarInfo(Cursor cursor) {
//        CarInfo carInfo = new CarInfo();
//        carInfo.setNamecar(cursor.getString(1));
//        carInfo.setBienso(cursor.getString(2));
//        carInfo.setDtxl(cursor.getString(3));
//        carInfo.setCaruser(cursor.getString(4));
//        return carInfo;
//    }
//}
