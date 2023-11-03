//package com.example.do_an.ui;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.model.HisCar;
//
//import java.util.Calendar;
//import java.util.List;
//
//public class DatLichActivity extends AppCompatActivity {
//    ImageButton imbbackDatLich;
//    TextView cannelDatLich;
//    EditText eddatlichdate, eddatlichtime, MotaLoied;
//    Button btDatLich;
//    CarHisDataSource mCarHisDataSource = new CarHisDataSource(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dat_lich);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        mCarHisDataSource.open();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        imbbackDatLich = findViewById(R.id.imbbackDatLich);
//        cannelDatLich = findViewById(R.id.cannelDatLich);
//        eddatlichdate = findViewById(R.id.eddatlichdate);
//        eddatlichtime = findViewById(R.id.eddatlichtime);
//        MotaLoied = findViewById(R.id.MotaLoied);
//        btDatLich = findViewById(R.id.btDatLich);
//        Intent intent = getIntent();
//        String localName = intent.getStringExtra("key");
//        //String localAddress = "111 CMT8, Quận 10, TP HCM";
//
//        eddatlichdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });
//        eddatlichtime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePickerDialog();
//            }
//        });
//        imbbackDatLich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        cannelDatLich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        btDatLich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (checkDate(eddatlichdate.getText().toString().trim(), username)) {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Trùng ngày đặt lịch đã đặt", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//                else if (checkDateTime(eddatlichdate.getText().toString().trim(), eddatlichtime.getText().toString().trim())) {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Đặt lịch thành công", Toast.LENGTH_SHORT);
//                    myToast.show();
//                    mCarHisDataSource.createHisCar(localName, eddatlichdate.getText().toString(), "Chưa hoàn thành", MotaLoied.getText().toString(), username);
//                    // mCarHisDataS
//                    mCarHisDataSource.close();
//                    Intent intent = new Intent(DatLichActivity.this, MainActivity.class);
//                    startActivity(intent);                    {
//                    }
//                } else {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Ngày hoặc giờ đặt lịch không hợp lệ", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//            }
//        });
//    }
//
//    private boolean checkDate(String date, String username) {
//        List<HisCar> hisCarList = mCarHisDataSource.getAllHisCar(username);
//        for (HisCar hisCar : hisCarList) {
//            if (hisCar.getDateFix().equals(date)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean checkDateTime(String date, String time) {
//        String[] arrOfDate = date.split("/"); // Cắt chuỗi bởi dấu /
//        String[] arrOfTime = time.split(":"); // Cắt chuỗi bởi dấu :
//        final Calendar c = Calendar.getInstance();
//        int currentYear = c.get(Calendar.YEAR);
//        int currentMonth = c.get(Calendar.MONTH) + 1;
//        int currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//        int selectedYear = Integer.valueOf(arrOfDate[2]);
//        int selectedMonth = Integer.valueOf(arrOfDate[1]);
//        int selectedDayOfMonth = Integer.valueOf(arrOfDate[0]);
//        int selectedHour = Integer.valueOf(arrOfTime[0]);
//        if (date.equals("") || time.equals(""))
//            return false;
//        if (selectedHour >= 7 && selectedHour <= 17)
//            if (selectedYear > currentYear ||
//                    (selectedYear == currentYear && selectedMonth > currentMonth) ||
//                    (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth > currentDayOfMonth) ||
//                    (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth > currentDayOfMonth)) {
//                return true;
//            } else
//                return false;
//        return false;
//    }
//
//
//    private void showTimePickerDialog() {
//        // Lấy thời gian hiện tại
//        final Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//
//        // Tạo hộp thoại TimePickerDialog
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        // Xử lý sự kiện khi người dùng chọn giờ
//                        String time = hourOfDay + ":" + minute;
//                        eddatlichtime.setText(time);
//                    }
//                }, hour, minute, true);
//
//        // Hiển thị hộp thoại TimePickerDialog
//        timePickerDialog.show();
//    }
//
//    private void showDatePickerDialog() {
//        // Lấy ngày hiện tại
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//
//        // Tạo hộp thoại DatePickerDialog
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Xử lý sự kiện khi người dùng chọn ngày
//                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
//                        eddatlichdate.setText(date);
//                    }
//                }, year, month, dayOfMonth);
//
//        // Hiển thị hộp thoại DatePickerDialog
//        datePickerDialog.show();
//    }
//
//
//}