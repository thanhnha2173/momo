package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.do_an.R;
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
import java.util.Locale;
import java.util.Map;

public class ThongKeAdminActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChart;
    private LinearLayout btnBackReport_admin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_admin);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        btnBackReport_admin = findViewById(R.id.btnBackReport_admin);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);

        btnBackReport_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new NewAxisValueFormatter());

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
                        BarDataSet barDataSet = new BarDataSet(monthlyEntries, "Thống kê số tiền giao dịch của các tháng");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData = new BarData(barDataSet);
                        xAxis.setValueFormatter(new NewAxisValueFormatter());

                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.animateY(2000);

                        float totalExpenseCurrentMonth = monthlyTotalMap.containsKey(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                ? monthlyTotalMap.get(Calendar.getInstance().get(Calendar.MONTH) + 1)
                                : 0;

                        CurrencyFormatter currencyFormatter = new CurrencyFormatter();
                    }
                    else {
                        Log.e("FirestoreData", "Error loading data", task.getException());
                    }
                });

        // ...

        db.collection("TransactionInfo") // Thay yourCollectionName bằng tên collection của bạn trong Firestore
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int titleCount = 0;
                        Map<String, Integer> iddataCountMap = new HashMap<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy dữ liệu từ mỗi document, kiểm tra và đếm số lượng title
                            String title = document.getString("iddata");
                            if (title != null && !title.isEmpty()) {
                                if (iddataCountMap.containsKey(title)) {
                                    int count = iddataCountMap.get(title);
                                    iddataCountMap.put(title, count + 1);
                                } else {
                                    iddataCountMap.put(title, 1);
                                }
                            }
                        }
                        ArrayList<PieEntry> entries = new ArrayList<>();
                        for (Map.Entry<String, Integer> entry : iddataCountMap.entrySet()) {
                            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                        }

                        // Tạo dataset và đặt màu sắc
                        PieDataSet pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                        // Tạo PieData và đặt các thuộc tính
                        PieData pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(12f);
                        pieData.setValueTextColor(Color.WHITE);

                        // Thiết lập PieData cho PieChart
                        pieChart.setData(pieData);

                        // Cài đặt mô tả cho PieChart (nếu cần thiết)
                        pieChart.setCenterText("Thống kê giao dịch");
                        pieChart.setCenterTextSize(18f);
                        pieChart.setHoleRadius(50f); // Kích thước lỗ tròn ở giữa PieChart

                        // Cập nhật PieChart
                        pieChart.invalidate();
                    } else {
                        Log.e("FirestoreData", "Error getting documents.", task.getException());
                    }
                });
        // ...
    }

    public class NewAxisValueFormatter extends ValueFormatter {
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