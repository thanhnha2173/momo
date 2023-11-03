package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.fragment.SettingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PersonalPageActivity extends AppCompatActivity {
    LinearLayout TTCN, TTLH;
    TextView hoten1, phone1;
    ImageButton backSetting;
    Button out2;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        hoten1 = findViewById(R.id.hoten1);
        out2 = findViewById(R.id.out2);
        phone1 = findViewById(R.id.phone1);
        phone1.setText(phoneNumber);
        getName(phoneNumber).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String hoTen) {
                // Use the hoTen value here or perform any action based on the result
                if(!hoTen.equals(""))
                    hoten1.setText(hoTen);
            }
        });
        TTCN = findViewById(R.id.TTCN);
        TTLH = findViewById(R.id.TTLH);
        backSetting = findViewById(R.id.backSetting);
        backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        out2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        TTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalPageActivity.this, PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        TTLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalPageActivity.this, ContactInfoActivity.class);
                startActivity(intent);
            }
        });
    }
    public LiveData<String> getName(String phoneNumber) {
        MutableLiveData<String> hoTenLiveData = new MutableLiveData<>();

        db.collection("UsersInfo").document("CN" + phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String hoTen = document.getString("HoTen");
                                hoTenLiveData.postValue(hoTen);
                            } else {
                                hoTenLiveData.postValue(""); // Document not found or HoTen is empty
                            }
                        } else {
                            hoTenLiveData.postValue(""); // Error occurred
                        }
                    }
                });

        return hoTenLiveData;
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PersonalPageActivity.this, EnterSdtActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT);
                myToast.show();
                finishAffinity(); // kết thúc tất cả các activity và xóa khỏi stack
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dismiss the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}