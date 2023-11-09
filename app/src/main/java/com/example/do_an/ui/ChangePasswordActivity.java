package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageButton backChangePass;
    EditText oldPass, newPass, renewPass;
    Button xacnhan;
    TextView quenPass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        backChangePass = findViewById(R.id.backChangePass);
        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        renewPass = findViewById(R.id.renewPass);
        xacnhan = findViewById(R.id.xacnhan);
        quenPass2 = findViewById(R.id.quenPass2);
        backChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        quenPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, RegisterActivity.class);
                intent.putExtra("check", "1");
                intent.putExtra("PHONE_NUMBER", phoneNumber);
                startActivity(intent);
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserInfo userInfo = new UserInfo("CN" + phoneNumber, Long.parseLong(String.valueOf(newPass.getText())));
                updateToFireStore(userInfo, phoneNumber);
            }
        });
    }


    private void updateToFireStore(UserInfo userInfo, String phoneNumber) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        // Trước khi thực hiện cập nhật, thực hiện truy vấn đọc để lấy dữ liệu hiện tại của người dùng
        db.collection("UsersInfo").document(userInfo.getMaTTCN() + "").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Lấy mật khẩu hiện tại của người dùng từ cơ sở dữ liệu
                                String currentPassword = document.getLong("MatKhau").toString();

                                // Kiểm tra xem oldPass có khớp với mật khẩu hiện tại hay không
                                if (oldPass.getText().toString().equals(currentPassword)) {
                                    // oldPass khớp, thực hiện truy vấn cập nhật
                                    if (oldPass.getText().toString().isEmpty() || newPass.getText().toString().isEmpty() || renewPass.getText().toString().isEmpty()) {
                                        Toast.makeText(ChangePasswordActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                                    } else if (newPass.getText().toString().equals(oldPass.getText().toString())) {
                                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không được trùng với mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
                                    } else if (!newPass.getText().toString().equals(renewPass.getText().toString())) {
                                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();

                                    } else if (newPass.getText().toString().length() < 6 || renewPass.getText().toString().length() < 6  || !newPass.getText().toString().matches("^[0-9]+$")) {
                                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải là 6 chữ số", Toast.LENGTH_SHORT).show();

                                    } else{


                                    db.collection("UsersInfo").document(userInfo.getMaTTCN() + "").update("MatKhau", userInfo.getMatKhau())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                                        intent.putExtra("PHONE_NUMBER", phoneNumber);
                                                        startActivity(intent);
                                                        Toast.makeText(ChangePasswordActivity.this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Khi lưu thất bại
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() { // Khi lưu thất bại
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    }

                                } else {
                                    // oldPass không khớp, hiển thị thông báo lỗi cho người dùng
                                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Error accessing Firestore
                            Toast.makeText(ChangePasswordActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}