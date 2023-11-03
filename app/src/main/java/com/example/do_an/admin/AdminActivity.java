//package com.example.do_an.admin;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.do_an.R;
//import com.example.do_an.ui.LoginActivity;
//import com.example.do_an.ui.RegisterActivity;
//
//public class AdminActivity extends AppCompatActivity {
//    Button GAUsers, GALich, logout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        // Khởi tạo đối tượng SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("isAdmin", MODE_PRIVATE);
//        // Lưu thông tin username vào SharedPreferences
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("admin", "1"); // "username" là tên key, username là giá trị
//        editor.apply();
//        GAUsers = findViewById(R.id.GAUsers);
//        GALich = findViewById(R.id.GALich);
//        logout = findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        GAUsers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this, AllUsersActivity.class);
//                startActivity(intent);
//            }
//        });
//        GALich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this, AllLichActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}