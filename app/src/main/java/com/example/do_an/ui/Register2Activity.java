package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.example.do_an.database.UserDataSource;
import com.example.do_an.database.UserInfoDataSource;
import com.example.do_an.model.CarInfo;
import com.example.do_an.model.User;
import com.example.do_an.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register2Activity extends AppCompatActivity {
    ImageButton imbbackRECtn;
    EditText edMK, edMK2, edSDT;
    Button btdkyREcnt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        imbbackRECtn = findViewById(R.id.imbbackRECon2);
        btdkyREcnt = findViewById(R.id.btdkyctn2);
        edMK = findViewById(R.id.edMK);
        edMK2 = findViewById(R.id.edMK2);
        edSDT = findViewById(R.id.edSDT);
        edSDT.setText(phoneNumber);

        long Username = Long.parseLong(phoneNumber);

        imbbackRECtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btdkyREcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edMK.getText().toString().isEmpty() || edMK2.getText().toString().isEmpty()) {
                    Toast.makeText(Register2Activity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!edMK.getText().toString().equals(edMK2.getText().toString())) {
                    Toast.makeText(Register2Activity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();

                } else if (edMK.getText().toString().length() < 6 || edMK2.getText().toString().length() < 6  || !edMK.getText().toString().matches("^[0-9]+$")) {
                    Toast.makeText(Register2Activity.this, "Mật khẩu phải là 6 chữ số", Toast.LENGTH_SHORT).show();

                } else{
                    User user = new User(Username, "CN" + Username, 0, 0, false);
                    UserInfo userInfo = new UserInfo("CN" + Username, "", "","","","","", Long.parseLong(String.valueOf(edMK.getText())));
                    saveToFireStore(user, userInfo);
                    Intent intent = new Intent(Register2Activity.this, LoginActivity.class);
                    intent.putExtra("PHONE_NUMBER", phoneNumber);
                    startActivity(intent);
                }

            }
        });
    }

    // Hàm cập nhật
    private void updateToFireStore(User mo) {
        db.collection("Documents").document(mo.getId() + "").update("soDuVi", mo.getSoduvi(), "soDuVi2", mo.getSodutuithantai())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register2Activity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register2Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Khi lưu thất bại
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // Khi lưu thất bại
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        userMap.put("IsLocked", user.isLocked());
        db.collection("Users").document(user.getId()+"").set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User data saved successfully
                            Toast.makeText(Register2Activity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            // Now, save userInfo data to "UsersInfo" collection
                            HashMap<String, Object> userInfoMap = new HashMap<>();
                            userInfoMap.put("MaTTCN", userInfo.getMaTTCN()); // Replace with actual property names
                            userInfoMap.put("HoTen", userInfo.getHoTen());
                            userInfoMap.put("Email", userInfo.getEmail());
                            userInfoMap.put("GioiTinh", userInfo.getGioiTinh());
                            userInfoMap.put("NgaySinh", userInfo.getNgaySinh());
                            userInfoMap.put("CCCD", userInfo.getCCCD());
                            userInfoMap.put("DiaChi", userInfo.getDiaChi());
                            userInfoMap.put("MatKhau", userInfo.getMatKhau());
                            // Add more properties as needed
                            db.collection("UsersInfo").document(userInfo.getMaTTCN()).set(userInfoMap);

                        } else {
                            Toast.makeText(Register2Activity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register2Activity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}