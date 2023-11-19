package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ThongTinCaNhanActivity extends AppCompatActivity {
    private TextView hoten;
    private TextView gioitinh;
    private TextView ngaysinh;
    private TextView cccd;
    private TextView email;
    private TextView diachi;
    private ImageButton bck_ttcn_admin;

    private FirebaseFirestore db;
    private String accountId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        bck_ttcn_admin = findViewById(R.id.bck_ttcn_admin);
        hoten = findViewById(R.id.hoten_admin);
        gioitinh = findViewById(R.id.gioitinh_admin);
        ngaysinh = findViewById(R.id.ngaysinh_admin);
        cccd = findViewById(R.id.cccd_admin);
        email = findViewById(R.id.email_admin);
        diachi = findViewById(R.id.diachi_admin);

        bck_ttcn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ACCOUNT_ID")) {
            accountId = intent.getStringExtra("ACCOUNT_ID");
            getPersonalInfo(accountId);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void getPersonalInfo(String accountId) {
        db.collection("UsersInfo").document(accountId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String hoTen = documentSnapshot.getString("HoTen");
                            String gioiTinh = documentSnapshot.getString("GioiTinh");
                            String ngaySinh = documentSnapshot.getString("NgaySinh");
                            String cccD = documentSnapshot.getString("CCCD");
                            String eMail = documentSnapshot.getString("Email");
                            String diaChi = documentSnapshot.getString("DiaChi");
                            // Lấy các giá trị khác tương tự cho các trường thông tin

                            // Hiển thị thông tin lên các TextViews
                            hoten.setText(hoTen);
                            gioitinh.setText(gioiTinh);
                            ngaysinh.setText(ngaySinh);
                            cccd.setText(cccD);
                            email.setText(eMail);
                            diachi.setText(diaChi);
                            Log.d("ThongTinCaNhanActivity", "ACCOUNT_ID: " + accountId);
                            // Gán các giá trị khác cho các TextViews tương ứng
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi không lấy được dữ liệu từ Firestore
                    }
                });
    }
}