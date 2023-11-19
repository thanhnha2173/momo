package com.example.do_an.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;

public class AdminActivity extends AppCompatActivity {
    private TextView logout_admin;

    LinearLayout taikhoan, uudai, phanhoi, thongke;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logout_admin = findViewById(R.id.logout_admin);
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
        logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    public void showDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AdminActivity.this, EnterSdtActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT);
                myToast.show();
                finishAffinity(); // kết thúc tất cả các activity và xóa khỏi stack
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dismiss the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}