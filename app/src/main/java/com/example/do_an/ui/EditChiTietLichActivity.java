//package com.example.do_an.ui;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
////import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.R;
//import com.example.do_an.model.HisCar;
//
//import java.util.Calendar;
//import java.util.List;
//
//public class EditChiTietLichActivity extends AppCompatActivity {
//    ImageButton imbbackInfoEdit;
//    TextView cannelDel, nameDel, dateDel, motaDel;
//    Button saveDel;
//    CarHisDataSource carHisDataSource = new CarHisDataSource(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_chi_tiet_lich);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        carHisDataSource.open();
//
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("phoneDel");
//        imbbackInfoEdit = findViewById(R.id.imbbackInfoEdit);
//        imbbackInfoEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        cannelDel = findViewById(R.id.cannelDel);
//        cannelDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        nameDel = findViewById(R.id.nameDel);
//        dateDel = findViewById(R.id.dateDel);
//        dateDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });
//        motaDel = findViewById(R.id.motaDel);
//        saveDel = findViewById(R.id.saveDel);
//        setTV(username);
//        saveDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkDate(dateDel.getText().toString().trim(), username)) {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Trùng ngày đặt lịch đã đặt", Toast.LENGTH_SHORT);
//                    myToast.show();
//                } else if (checkDateTime(dateDel.getText().toString().trim())) {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Thay đổi lịch thành công", Toast.LENGTH_SHORT);
//                    myToast.show();
//                    updateHisCal(username, nameDel.getText().toString(), dateDel.getText().toString(), motaDel.getText().toString());
//                    Intent intent = new Intent(EditChiTietLichActivity.this, ChiTietLichActivity.class);
//                    intent.putExtra("id", username + "-" + dateDel.getText());
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast myToast = Toast.makeText(getApplicationContext(), "Ngày đặt lịch không hợp lệ", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//            }
//        });
//
//    }
//
//    public void updateHisCal(String username, String nameDel, String dataDel, String motaDel) {
//        CarHisDataSource hisDataSource = new CarHisDataSource(this);
//        hisDataSource.open();
//        List<HisCar> hisCars = hisDataSource.getAllHisCar(username);
//        Log.d("1", username);
//        for (HisCar hisCar : hisCars) {
//            if (hisCar.getHisuser().equals(username)) {
//                Log.d("2", hisCar.getHisuser());
//                carHisDataSource.updateHisCar(hisCar, nameDel, dataDel, "Chưa hoàn thành", motaDel, username);
//            }
//        }
//        carHisDataSource.close();
//    }
//
//    private void setTV(String username) {
//        List<HisCar> hisCars = carHisDataSource.getAllHisCar(username);
//        for (HisCar hisCar : hisCars) {
//            if (hisCar.getHisuser().equals(username)) {
//                nameDel.setText(hisCar.getNameLocal());
//                motaDel.setText(hisCar.getMota());
//                dateDel.setText(hisCar.getDateFix());
//            }
//        }
//    }
//
//    private boolean checkDate(String date, String username) {
//        List<HisCar> hisCarList = carHisDataSource.getAllHisCar(username);
//        for (HisCar hisCar : hisCarList) {
//            if (hisCar.getDateFix().equals(date)) {
//                return true;
//            }
//        }
//        return false;
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
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth > currentDayOfMonth) ||
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth > currentDayOfMonth)) {
//            return true;
//        } else
//            return false;
//    }
//    public void showPopupMenu(View v) {
//        PopupMenu popupMenu = new PopupMenu(this, v);
//        popupMenu.getMenuInflater().inflate(R.menu.popmenulocal, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                String gender = item.getTitle().toString();
//                EditText genderEditText = findViewById(R.id.nameDel);
//                genderEditText.setText(gender);
//                return true;
//            }
//        });
//        popupMenu.show();
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
//                        dateDel.setText(date);
//                    }
//                }, year, month, dayOfMonth);
//
//        // Hiển thị hộp thoại DatePickerDialog
//        datePickerDialog.show();
//    }
//
//}