package com.example.do_an.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class EnterSdtActivity extends AppCompatActivity {

    private EditText edPhone;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sdt);
        FirebaseApp.initializeApp(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edPhone = findViewById(R.id.edphone);
        db = FirebaseFirestore.getInstance();
        Button btnContinue = findViewById(R.id.btdkyctn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edPhone.getText().toString().trim();
                if (phoneNumber.equals("0000000000")) {
                    Intent intent = new Intent(EnterSdtActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    if (phoneNumber.length() != 10)
                        Toast.makeText(EnterSdtActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    else if (!phoneNumber.isEmpty()) {
                        checkPhone(phoneNumber);
                    } else {
                        Toast.makeText(EnterSdtActivity.this, "Chưa nhập SĐT", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    void checkPhone(String phoneNumber){
        db = FirebaseFirestore.getInstance(); // Khởi tạo db ở đây
        if (phoneNumber.equals("0000000000")) {
            Intent intent = new Intent(EnterSdtActivity.this, AdminActivity.class);
            startActivity(intent);
        } else {
            db.collection("Users").document(phoneNumber).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    Boolean isLocked = document.getBoolean("IsLocked");
                                    if (isLocked != null && isLocked) {
                                        // Nếu tài khoản bị khóa, hiển thị thông báo và không cho phép đăng nhập
                                        Toast.makeText(EnterSdtActivity.this, "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên để biết thêm chi tiết.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Nếu tài khoản không bị khóa, tiếp tục với quá trình đăng nhập
                                        Intent loginIntent = new Intent(EnterSdtActivity.this, LoginActivity.class);
                                        loginIntent.putExtra("PHONE_NUMBER", phoneNumber);
                                        Toast.makeText(EnterSdtActivity.this, "Vui lòng nhập mật khẩu để đăng nhập tài khoản", Toast.LENGTH_SHORT).show();
                                        startActivity(loginIntent);
                                    }
                                } else {
                                    // Nếu không có tài khoản, chuyển sang màn hình đăng ký
                                    // ...
                                }
                            } else {
                                // Xử lý khi truy vấn không thành công
                                // ...
                            }
                        }
                    });
        }
    }

}
