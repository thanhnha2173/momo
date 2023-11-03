package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChuyenTienActivity extends AppCompatActivity {
    EditText sdtCT, soduviNT, ndCT;
    Button btCT;
    ImageButton backLogin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_tien);
        ActionBar actionBar = getSupportActionBar();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber1 = sharedPreferences.getString("PHONE_NUMBER", "");
        actionBar.hide();
        btCT = findViewById(R.id.btCT);
        sdtCT = findViewById(R.id.sdtCT);
        soduviNT = findViewById(R.id.soduviNT);
        ndCT = findViewById(R.id.ndCT);
        backLogin1 = findViewById(R.id.backLogin1);
        btCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = sdtCT.getText().toString();
                String amountStr = soduviNT.getText().toString();

                if (phoneNumber.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(ChuyenTienActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.parseInt(amountStr);
                    transferMoney(phoneNumber1,phoneNumber, amount);
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
    private void transferMoney(String senderPhoneNumber, String receiverPhoneNumber, int amount) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(senderPhoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot senderDocument = task.getResult();
                            if (senderDocument.exists()) {
                                int senderBalance = senderDocument.getLong("soDuVi").intValue();
                                if (senderBalance >= amount) {
                                    int newSenderBalance = senderBalance - amount;

                                    // Update sender's balance
                                    db.collection("Users").document(senderPhoneNumber)
                                            .update("soDuVi", newSenderBalance)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Now update receiver's balance
                                                        updateReceiverBalance(receiverPhoneNumber, amount);
                                                    } else {
                                                        Toast.makeText(ChuyenTienActivity.this, "Chuyển tiền thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(ChuyenTienActivity.this, "Số dư không đủ để thực hiện giao dịch", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ChuyenTienActivity.this, "Không tìm thấy tài khoản chuyển tiền", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChuyenTienActivity.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateReceiverBalance(String receiverPhoneNumber, int amount) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(receiverPhoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot receiverDocument = task.getResult();
                            if (receiverDocument.exists()) {
                                int receiverBalance = receiverDocument.getLong("soDuVi").intValue();
                                int newReceiverBalance = receiverBalance + amount;

                                // Update receiver's balance
                                db.collection("Users").document(receiverPhoneNumber)
                                        .update("soDuVi", newReceiverBalance)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChuyenTienActivity.this, "Chuyển tiền thành công", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(ChuyenTienActivity.this, "Chuyển tiền thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(ChuyenTienActivity.this, "Không tìm thấy tài khoản nhận tiền", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ChuyenTienActivity.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}