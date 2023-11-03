//package com.example.do_an.admin;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageButton;
//
//import com.example.do_an.R;
//import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.model.HisApdater;
//import com.example.do_an.model.HisCar;
//import com.example.do_an.model.HisCarDateComparator;
//import com.example.do_an.ui.ChiTietLichActivity;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.List;
//
//public class AllLichActivity extends AppCompatActivity implements HisApdater.UserCallback {
//    ImageButton imbackadlich;
//    RecyclerView rvlistLich;
//    ArrayList<HisCar> listHisCar;
//    HisApdater hisApdater;
//    CarHisDataSource carHisDataSource;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_lich);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        imbackadlich = findViewById(R.id.imbackadlich);
//        rvlistLich = findViewById(R.id.rvlistLich);
//        imbackadlich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AllLichActivity.this, AdminActivity.class);
//                startActivity(intent);
//                finish();
//                //return;
//            }
//        });
//        CarHisDataSource carHisDataSource = new CarHisDataSource(this);
//        carHisDataSource.open();
//        List<HisCar> hisCars = carHisDataSource.getAllHisCarAdmin();
//        if(!hisCars.isEmpty())
//        {
//            for (HisCar hisCar : hisCars) {
//                Log.d("ad", "1");
//                if (!checkDateTime(hisCar.getDateFix())) {
//                    Log.d("ad", "2");
//                    hisCar.setStatus("Đã hoàn thành");
//                    carHisDataSource.updateHisCarStatus(hisCar, "Đã hoàn thành");
//                }
//                else
//                {
//                    hisCar.setStatus("Chưa hoàn thành");
//                    carHisDataSource.updateHisCarStatus(hisCar, "Chưa hoàn thành");
//                }
//            }
//            carHisDataSource.close();
//        }
//        listHisCar = new ArrayList<>(hisCars);
//        Collections.sort(listHisCar, new HisCarDateComparator());
//         hisApdater = new HisApdater(listHisCar, (this));
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvlistLich.setAdapter(hisApdater);
//        rvlistLich.setLayoutManager(linearLayoutManager);
//    }
//    @Override
//    public void onItemClick(String id) {
//        Intent intent = new Intent(this, ChiTietLichActivity.class);
//        intent.putExtra("id", id);
//        startActivity(intent);
//    }
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
//}