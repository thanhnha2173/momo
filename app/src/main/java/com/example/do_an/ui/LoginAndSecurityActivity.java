package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.do_an.R;

public class LoginAndSecurityActivity extends AppCompatActivity {
    LinearLayout changePass1, lockAcc;
    ImageButton backLogin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_security);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        changePass1 = findViewById(R.id.changePass1);
        backLogin1 = findViewById(R.id.backLogin1);
        backLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lockAcc = findViewById(R.id.lockAcc);
        changePass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAndSecurityActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        lockAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAndSecurityActivity.this, LockAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}