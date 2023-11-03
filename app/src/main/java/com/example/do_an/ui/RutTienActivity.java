package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.example.do_an.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RutTienActivity extends AppCompatActivity {
    TextView soduviNT, soduviXem;
    Button btnt;
    ImageButton backLogin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut_tien);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");

        btnt = findViewById(R.id.btRT);
        soduviNT = findViewById(R.id.soduviRT);
        soduviXem = findViewById(R.id.soduviXem);
        backLogin1 = findViewById(R.id.backLogin1);
        getInfo(phoneNumber);
        btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soduviNT.getText() == null)
                    Toast.makeText(RutTienActivity.this, "Chưa nhập số tiền cần rút", Toast.LENGTH_SHORT).show();
                else{
                    int sodu = Integer.parseInt(String.valueOf(soduviNT.getText()));
                    updateToFireStore(phoneNumber,sodu);
                }
            }
        });
        backLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void updateToFireStore(String id, int sodu) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        // Tạo một tham chiếu đến tài khoản người dùng cần cập nhật
        DocumentReference userRef = db.collection("Users").document(id);

        // Lấy dữ liệu hiện tại từ Firestore
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        long currentSoDu = document.getLong("soDuVi").intValue();
                        if(currentSoDu < sodu)
                            Toast.makeText(RutTienActivity.this, "Số tiền rút lớn hơn số dư", Toast.LENGTH_SHORT).show();
                        else {
                            long newSoDu = currentSoDu - sodu;
                            // Cập nhật số dư mới vào Firestore
                            userRef.update("soDuVi", newSoDu)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RutTienActivity.this, "Rút tiền thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(RutTienActivity.this, "Lỗi khi rút tiền: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(RutTienActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RutTienActivity.this, "Lỗi khi truy cập dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void getInfo(String phoneNumber) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                long sodu = document.getLong("soDuVi");
                                if (sodu >= 1000)
                                    soduviXem.setText("Số dư ví: " + String.format("%,d", sodu) + "đ"); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                                else
                                    soduviXem.setText(String.valueOf(sodu)); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                            }
                        }
                    }
                });
    }
}