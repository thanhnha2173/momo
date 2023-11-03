package com.example.do_an.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an.R;
import com.example.do_an.model.User;
import com.example.do_an.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class QuenPassActivity extends AppCompatActivity {
    ImageButton imbbackQuenPass;
    EditText edMK1, edMK3, edSDT1;
    Button btdone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_pass);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        imbbackQuenPass = findViewById(R.id.imbbackQuenPass);
        btdone = findViewById(R.id.btdone);
        edMK1 = findViewById(R.id.edMK1);
        edMK3 = findViewById(R.id.edMK3);
        edSDT1 = findViewById(R.id.edSDT1);
        edSDT1.setText(phoneNumber);

        long Username = Long.parseLong(phoneNumber);

        imbbackQuenPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edMK1.getText().toString().isEmpty() || edMK3.getText().toString().isEmpty()) {
                    Toast.makeText(QuenPassActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!edMK1.getText().toString().equals(edMK3.getText().toString())) {
                    Toast.makeText(QuenPassActivity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();

                } else if (edMK1.getText().toString().length() < 6 || edMK3.getText().toString().length() < 6  || !edMK1.getText().toString().matches("^[0-9]+$")) {
                    Toast.makeText(QuenPassActivity.this, "Mật khẩu phải là 6 chữ số", Toast.LENGTH_SHORT).show();

                } else{
                    UserInfo userInfo = new UserInfo("CN" + Username, Long.parseLong(String.valueOf(edMK1.getText())));
                    updateToFireStore(userInfo, phoneNumber);

                }

            }
        });

    }

    // Hàm cập nhật
    private void updateToFireStore(UserInfo userInfo, String phoneNumber) {
        db.collection("UsersInfo").document(userInfo.getMaTTCN() + "").update("MatKhau", userInfo.getMatKhau())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(QuenPassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QuenPassActivity.this, LoginActivity.class);
                            intent.putExtra("PHONE_NUMBER", phoneNumber);
                            startActivity(intent);
                        } else {
                            Toast.makeText(QuenPassActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Khi lưu thất bại
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // Khi lưu thất bại
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuenPassActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Hàm lưu dữ liệu lên FireStore

    private void saveToFireStore(User user, UserInfo userInfo) {
        // Save user data to "Users" collection
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("soTK", user.getId());
        userMap.put("MaTTCN", user.getMaTTCN());
        userMap.put("soDuVi", user.getSoduvi());
        userMap.put("soDuVi2", user.getSodutuithantai());
        db.collection("Users").document(user.getId()+"").set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User data saved successfully
                            Toast.makeText(QuenPassActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            // Now, save userInfo data to "UsersInfo" collection
                            HashMap<String, Object> userInfoMap = new HashMap<>();
                            userInfoMap.put("MaTTCN", userInfo.getMaTTCN()); // Replace with actual property names
                            userInfoMap.put("HoTen", userInfo.getHoTen());
                            userInfoMap.put("Email", userInfo.getEmail());
                            userInfoMap.put("GioiTinh", userInfo.getGioiTinh());
                            userInfoMap.put("NgaySinh", userInfo.getNgaySinh());
                            userInfoMap.put("DiaChi", userInfo.getDiaChi());
                            userInfoMap.put("MatKhau", userInfo.getMatKhau());
                            // Add more properties as needed
                            db.collection("UsersInfo").document(userInfo.getMaTTCN()).set(userInfoMap);

                        } else {
                            Toast.makeText(QuenPassActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuenPassActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}