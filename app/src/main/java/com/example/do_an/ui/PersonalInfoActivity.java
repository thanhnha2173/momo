package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.example.do_an.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class PersonalInfoActivity extends AppCompatActivity {
    ImageButton backTTCN;
    TextView LuuTTCN;
    EditText hotenTTCN, phoneTTCN, sn, cccd, sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        backTTCN = findViewById(R.id.backTTCN);
        LuuTTCN = findViewById(R.id.LuuTTCN);
        hotenTTCN = findViewById(R.id.hotenTTCN);
        phoneTTCN = findViewById(R.id.phoneTTCN);
        sn = findViewById(R.id.sn);
        sex = findViewById(R.id.sex1);
        cccd = findViewById(R.id.cccd);
        getInfo(phoneNumber);
        backTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        LuuTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = new UserInfo("CN" + phoneNumber, hotenTTCN.getText().toString(),sex.getText().toString(),sn.getText().toString(),cccd.getText().toString());
                updateToFireStore(userInfo);
            }
        });
    }
    void getInfo(String phoneNumber){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("UsersInfo").document("CN" + phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String hoTen = document.getString("HoTen");
                                hotenTTCN.setText(hoTen);
                                phoneTTCN.setText(phoneNumber);
                                String sn1 = document.getString("NgaySinh");
                                sn.setText(sn1);
                                String cccd1 = document.getString("CCCD");
                                cccd.setText(cccd1);
                                String sex1 = document.getString("GioiTinh");
                                sex.setText(sex1);
                            }
                        }
                    }
                });
    }
    private void updateToFireStore(UserInfo userInfo) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("UsersInfo").document(userInfo.getMaTTCN() + "").update("HoTen", userInfo.getHoTen(), "NgaySinh", userInfo.getNgaySinh(), "GioiTinh", userInfo.getGioiTinh(), "CCCD", userInfo.getCCCD())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PersonalInfoActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PersonalInfoActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Khi lưu thất bại
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // Khi lưu thất bại
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersonalInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popmenulocal, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String gender = item.getTitle().toString();
                EditText genderEditText = findViewById(R.id.sex1);
                genderEditText.setText(gender);
                return true;
            }
        });
        popupMenu.show();
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        // Tạo hộp thoại DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý sự kiện khi người dùng chọn ngày
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        sn.setText(date);
                    }
                }, year, month, dayOfMonth);

        // Hiển thị hộp thoại DatePickerDialog
        datePickerDialog.show();
    }

}