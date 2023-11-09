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
       // db.clearPersistence();
       // FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("Users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                // Xóa mỗi tài liệu trong collection "Users"
//                                db.collection("Users").document(document.getId()).delete();
//                            }
//                            Toast.makeText(EnterSdtActivity.this, "Đã xóa toàn bộ dữ liệu trong collection 'Users'", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(EnterSdtActivity.this, "Lỗi khi truy vấn dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        edPhone = findViewById(R.id.edphone);
        Button btnContinue = findViewById(R.id.btdkyctn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edPhone.getText().toString().trim();
                if (phoneNumber.length() != 10)
                    Toast.makeText(EnterSdtActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                else if (!phoneNumber.isEmpty()) {
                    checkPhone(phoneNumber);
                }
                else {
                    Toast.makeText(EnterSdtActivity.this, "Chưa nhập SĐT", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void checkPhone(String phoneNumber){
        db.setLoggingEnabled(true);
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                    Intent intent = new Intent(EnterSdtActivity.this, LoginActivity.class);
                                    intent.putExtra("PHONE_NUMBER", phoneNumber);
                                Toast.makeText(EnterSdtActivity.this, "Vui lòng nhập mật khẩu để đăng nhập tài khoản", Toast.LENGTH_SHORT).show();
                                startActivity(intent);  
                                
                            } else {
                                Intent intent = new Intent(EnterSdtActivity.this, RegisterActivity.class);
                                Toast.makeText(EnterSdtActivity.this, "Vui lòng nhập mã OTP để đăng ký tài khoản", Toast.LENGTH_SHORT).show();
                                intent.putExtra("PHONE_NUMBER", phoneNumber);
                                intent.putExtra("check2", "ok");
                                startActivity(intent);
                            }
                        } else {
                            // Error accessing Firestore
                            Toast.makeText(EnterSdtActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    };
}
