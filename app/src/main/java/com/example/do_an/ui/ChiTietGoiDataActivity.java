package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.example.do_an.model.MenuCollection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChiTietGoiDataActivity extends AppCompatActivity {

    private ImageButton dedataback;
    private Button btbBuyData;
    private TextView dataprice;
    private MenuCollection menuCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_goi_data);

        MenuCollection menuCollection = getIntent().getParcelableExtra("menuCollection");

        dedataback = findViewById(R.id.dedataback);
        btbBuyData = findViewById(R.id.btnBuyData);
        String title = getIntent().getStringExtra("title");
        TextView dataprice = findViewById(R.id.dataprice);
        dataprice.setText(title);

        btbBuyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getIntent().getStringExtra("title");
                String priceString = title.replace(" Đ", "").replace(".", "").replace(",", "").trim();
                int price = Integer.parseInt(priceString);

                SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("PHONE_NUMBER", "");

                updateBalance(userId, price);
                Intent intent = new Intent(ChiTietGoiDataActivity.this, PaymentSuccessActivity.class);

                intent.putExtra("dataprice", title);

                intent.putExtra("menuCollection", menuCollection);

                startActivity(intent);
            }
        });

        dedataback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void updateBalance(String userId, int amountToSubtract) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(userId);
        Log.d("ChiTietGoiDataActivity", "User ID: " + userId); // Ghi log về ID người dùng
        Log.d("ChiTietGoiDataActivity", "Amount to Subtract: " + amountToSubtract); // Ghi log về số tiền trừ


        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        long currentBalance = document.getLong("soDuVi");
                        if (currentBalance >= amountToSubtract) {
                            long newBalance = currentBalance - amountToSubtract;
                            // Cập nhật số dư mới vào Firestore
                            userRef.update("soDuVi", newBalance)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Hiển thị thông báo số dư đã được cập nhật thành công
                                                Toast.makeText(ChiTietGoiDataActivity.this, "Số dư đã được cập nhật!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Xử lý khi cập nhật không thành công
                                                Toast.makeText(ChiTietGoiDataActivity.this, "Lỗi khi cập nhật số dư: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Hiển thị thông báo nếu số dư không đủ
                            Toast.makeText(ChiTietGoiDataActivity.this, "Số dư không đủ!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Hiển thị thông báo nếu không tìm thấy người dùng
                        Toast.makeText(ChiTietGoiDataActivity.this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Hiển thị thông báo khi có lỗi truy cập dữ liệu
                    Toast.makeText(ChiTietGoiDataActivity.this, "Lỗi khi truy cập dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}