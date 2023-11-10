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
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChuyenTienActivity extends AppCompatActivity {
    EditText sdtCT, soduviCT, ndCT;
    TextView iddataCT;
    Button btCT;
    ImageButton backLogin1;
    private String date, hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_tien);
        ActionBar actionBar = getSupportActionBar();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber1 = sharedPreferences.getString("PHONE_NUMBER", "");
        actionBar.hide();

        iddataCT = findViewById(R.id.iddataCT);
        btCT = findViewById(R.id.btCT);
        sdtCT = findViewById(R.id.sdtCT);
        soduviCT = findViewById(R.id.soduviCT);
        ndCT = findViewById(R.id.ndCT);
        backLogin1 = findViewById(R.id.backLogin1);
        btCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iddata = iddataCT.getText().toString();
                String price = soduviCT.getText().toString();
                date = getCurrentDateAsString();
                hour = getCurrentTime();
                String phoneNumber = sdtCT.getText().toString();
                String amountStr = soduviCT.getText().toString();

                if (phoneNumber.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(ChuyenTienActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.parseInt(amountStr);
                    transferMoney(phoneNumber1,phoneNumber, amount);
                    updateNotification(iddata, price, date, hour);
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
    private String getCurrentDateAsString() {
        // Lấy ngày và giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Định dạng ngày theo định dạng dd/MM/yyyy
        return simpleDateFormat.format(calendar.getTime());
    }
    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // Format the time as a string
        String currentTime = String.format("%02d:%02d:%02d", hour, minute, second);

        return currentTime;
    }

    private void updateNotification(String titletran, String pricetran, String date, String hour){
        String formatedPrice = formatCurrencyFromString(pricetran);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> notificationMap = new HashMap<>();
        notificationMap.put("iddata", titletran);
        notificationMap.put("pricetran", formatedPrice);
        notificationMap.put("date", date);
        notificationMap.put("hour", hour);

        db.collection("TransactionInfo").add(notificationMap)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ChuyenTienActivity.this, "Chuyển tiền thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ChuyenTienActivity.this, "Lỗi khi đẩy thông tin lên Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public String formatCurrency(int amount) {
        String currency = String.format("%,d", amount); // Định dạng số nguyên thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
        return currency + " Đ"; // Thêm ký hiệu tiền tệ vào chuỗi
    }
    public String formatCurrencyFromString(String amountString){
        try {
            int amount = Integer.parseInt(amountString);
            return formatCurrency(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Invalid amount";
        }
    }
}