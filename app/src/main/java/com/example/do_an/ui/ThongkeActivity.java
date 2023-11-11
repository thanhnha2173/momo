package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.do_an.R;
import com.example.do_an.model.ThongBaoModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongkeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        LinearLayout btnBackReport = findViewById(R.id.btnBackReport);
        BarChart barChart = findViewById(R.id.barChart);

        btnBackReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        FirebaseFirestore db  = FirebaseFirestore.getInstance();
        db.collection("TransactionInfo").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<BarEntry> transaction = new ArrayList<>();
                        Map<Integer, Float> monthlyTotalMap = new HashMap<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String priceString = document.getString("pricetran");
                            String dateString = document.getString("date");

                            priceString = priceString.replaceAll("[^0-9]", "");

                            //Chuyển đổi chuỗi ngày thành kiểu Date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date date;
                            // Chuyển đổi chuỗi giá trị 'priceString' và 'dateString' thành kiểu số
                            try {
                                float price = Float.parseFloat(priceString);
                                date = dateFormat.parse(dateString);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cộng thêm 1

                                //Tính tổng giá trị theo tháng
                                if (monthlyTotalMap.containsKey(month)) {
                                    float total = monthlyTotalMap.get(month);
                                    total += price;
                                    monthlyTotalMap.put(month, total);
                                } else {
                                    monthlyTotalMap.put(month, price);
                                }
                                String monthString = String.valueOf(month);
                                float monthFloat = Float.parseFloat(monthString);

                                transaction.add(new BarEntry(price, monthFloat));
                            } catch (NumberFormatException | ParseException e) {
                                // Xử lý nếu có lỗi chuyển đổi kiểu số
                                e.printStackTrace();
                                Log.e("FirestoreData", "Error parsing price" + priceString, e);
                            }
                        }
                        Log.d("FirestoreData", "Data loaded successfully");
                        Log.d("BarChart", "Transaction size: " + transaction.size());

                        for (BarEntry entry : transaction) {
                            Log.d("BarChart", "Entry: x=" + entry.getX() + ", y=" + entry.getY());
                        }

                        ArrayList<BarEntry> monthlyEntries = new ArrayList<>();
                        for (Map.Entry<Integer, Float> entry : monthlyTotalMap.entrySet()) {
                            int month = entry.getKey();
                            float total = entry.getValue();
                            monthlyEntries.add(new BarEntry(month, total));
                        }

                        //Tạo BarDataSet và BarData
                        BarDataSet barDataSet = new BarDataSet(monthlyEntries, "Monthly Transactions");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData = new BarData(barDataSet);

                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Monthly Expense Chart");
                        barChart.animateY(2000);
                    }
                    else {
                        Log.e("FirestoreData", "Error loading data", task.getException());
                    }
                });
    }
}