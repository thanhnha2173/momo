package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    Button btlogin;
    TextView hello, changePhone, quenPass;
    EditText edpass;
    ImageButton backLogin;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Khởi tạo đối tượng SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", MODE_PRIVATE);
        // Lưu thông tin username vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        btlogin = findViewById(R.id.btlogin);
        backLogin = findViewById(R.id.backLogin);
        changePhone = findViewById(R.id.changePhone);
        hello = findViewById(R.id.hello);
        quenPass = findViewById(R.id.quenPass);
        edpass = findViewById(R.id.edpass);
        hello.setText("Xin chào, " + phoneNumber);

        db = FirebaseFirestore.getInstance();
        quenPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Toast.makeText(LoginActivity.this, "Vui lòng nhập mã OTP để tạo lại mật khẩu", Toast.LENGTH_SHORT).show();
                intent.putExtra("PHONE_NUMBER", phoneNumber);
                intent.putExtra("check", "1");
                startActivity(intent);
            }
        });
        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EnterSdtActivity.class);
                startActivity(intent);
                finish();
            }
        });
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edpass.getText().toString().trim();
                //Log.d("TAG", phoneNumber); // Debug log

                // Create a Firestore reference to the UserInfo collection
                db.collection("UsersInfo").document("CN" + phoneNumber).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String storedPassword = document.getLong("MatKhau").toString();
                                        if (storedPassword != null && storedPassword.equals(password)) {
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("PHONE_NUMBER", phoneNumber);
                                            editor.putString("PHONE_NUMBER", phoneNumber); // "username" là tên key, username là giá trị
                                            editor.apply();
                                            startActivity(intent);
                                            finishAffinity();
                                        } else {
                                            // Password does not match
                                            Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Document does not exist
                                        Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Error accessing Firestore
                                    Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
