package com.example.do_an.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.do_an.R;

public class AdminActivity extends AppCompatActivity {

    LinearLayout taikhoan, uudai, phanhoi, thongke;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        taikhoan = findViewById(R.id.taikhoan_admin);
        uudai = findViewById(R.id.uudai_admin);
        phanhoi = findViewById(R.id.phanhoi_admin);
        thongke = findViewById(R.id.thongke_admin);

        taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, TaiKhoanAdminActivity.class);
                startActivity(intent);
            }
        });
        uudai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, UuDaiAdminActivity.class);
                startActivity(intent);
            }
        });
//        phanhoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminActivity.this, PhanHoiAdminActivity.class);
//                startActivity(intent);
//            }
//        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ThongKeAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}