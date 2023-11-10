package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NapTienActivity extends AppCompatActivity {
    TextView soduviNT, iddataNT, soduViMomo;
    Button btnt;
    ImageButton backLogin1;
    private String date, hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        soduViMomo = findViewById(R.id.soduViMomo);
        iddataNT = findViewById(R.id.iddataNT);
        btnt = findViewById(R.id.btNT);
        soduviNT = findViewById(R.id.soduviNT);
        backLogin1 = findViewById(R.id.backLogin1);
        getInfo(phoneNumber);
        btnt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                date = getCurrentDateAsString();
                hour = getCurrentTime();
                String iddata = iddataNT.getText().toString();
                String price = soduviNT.getText().toString();
                if(soduviNT.getText() == null)
                    Toast.makeText(NapTienActivity.this, "Chưa nhập số tiền cần nạp", Toast.LENGTH_SHORT).show();
                else{
                    int sodu = Integer.parseInt(String.valueOf(soduviNT.getText()));
                    if(sodu < 10000)
                        Toast.makeText(NapTienActivity.this, "Nạp tối thiểu 10.000đ", Toast.LENGTH_SHORT).show();
                    else
                        updateToFireStore(phoneNumber,sodu);
                        updateNotification(iddata, price, date, hour);
                }
            }
        });
        backLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
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
                        long newSoDu = currentSoDu + sodu;
                        // Cập nhật số dư mới vào Firestore
                        userRef.update("soDuVi", newSoDu)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(NapTienActivity.this, "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(NapTienActivity.this, "Lỗi khi nạp tiền: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(NapTienActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NapTienActivity.this, "Lỗi khi truy cập dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                                    soduViMomo.setText(String.format("%,d", sodu) + "đ"); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                                else
                                    soduViMomo.setText(String.valueOf(sodu)); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                            }
                        }
                    }
                });
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
                    Toast.makeText(NapTienActivity.this, "Thông tin đã được đẩy lên Firebase", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(NapTienActivity.this, "Lỗi khi đẩy thông tin lên Firebase: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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