package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.fragment.TransHisFragment;
import com.example.do_an.model.ThongBaoModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThongkeActivity extends AppCompatActivity {
    private TextView tongchitieu;
    private LinearLayout lsgdlist, btnBackReport;
    private BarChart barChart;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        lsgdlist = findViewById(R.id.lsgdlist);
        tongchitieu = findViewById(R.id.tongchitieu);
        btnBackReport = findViewById(R.id.btnBackReport);
        barChart = findViewById(R.id.barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IntergerAxisValueFormatter());

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
                            } catch (NumberFormatException | ParseException e) {
                                // Xử lý nếu có lỗi chuyển đổi kiểu số
                                e.printStackTrace();
                                Log.e("FirestoreData", "Error parsing price" + priceString, e);
                            }
                        }

                        ArrayList<BarEntry> monthlyEntries = new ArrayList<>();
                        for (int i = 1; i <= 12; i++) {
                            float total = monthlyTotalMap.containsKey(i) ? monthlyTotalMap.get(i) : 0;
                            monthlyEntries.add(new BarEntry(i, total));
                        }

                        Collections.sort(monthlyEntries, new Comparator<BarEntry>() {
                            @Override
                            public int compare(BarEntry entry1, BarEntry entry2) {
                                return Float.compare(entry1.getX(), entry2.getX());
                            }
                        });

                        //Tạo BarDataSet và BarData
                        BarDataSet barDataSet = new BarDataSet(monthlyEntries, "Monthly Transactions");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData = new BarData(barDataSet);
                        barData.setValueFormatter(new CurrencyFormatter());

                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.animateY(2000);

                        float totalExpenseCurrentMonth = monthlyTotalMap.containsKey(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                ? monthlyTotalMap.get(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                : 0;

                        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
                        tongchitieu.setText(currencyFormatter.getFormattedValue(totalExpenseCurrentMonth));
                    }
                    else {
                        Log.e("FirestoreData", "Error loading data", task.getException());
                    }
                });

    }

    public class IntergerAxisValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.valueOf((int) value);
        }
    }

    public class CurrencyFormatter extends ValueFormatter {
        private final DecimalFormat format;

        public CurrencyFormatter() {
            this.format = new DecimalFormat("#,###.###đ", new DecimalFormatSymbols(Locale.US));
        }

        @Override
        public String getFormattedValue(float value) {
            return format.format(value);
        }
    }

}