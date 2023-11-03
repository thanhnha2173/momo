//package com.example.do_an.ui;
//
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.model.UserInfo;
//
//import java.util.List;
//
//public class InfoUserActivity extends AppCompatActivity {
//    ImageButton imbbackInfo;
//    TextView fix, name, phone, date, sex, address;
//    private UserInfoDataSource mInfoDataSource;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info_user);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        imbbackInfo = findViewById(R.id.imbbackInfo);
//        fix = findViewById(R.id.fix);
//        name = findViewById(R.id.name);
//        phone = findViewById(R.id.phone);
//        date = findViewById(R.id.date);
//        sex = findViewById(R.id.sex);
//        address = findViewById(R.id.address);
//        setTV();
//        imbbackInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(InfoUserActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
//        fix.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(InfoUserActivity.this, EditUserInfoActivity.class);
//                startActivity(i);
//            }
//        });
//
//    }
//
//    private void setTV() {
//        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        mInfoDataSource = new UserInfoDataSource(this);
//        mInfoDataSource.open();
//        List<UserInfo> userInfos = mInfoDataSource.getAllUserInfos();
//        for (UserInfo userInfo : userInfos) {
//            if (userInfo.getPhone().equals(username)) {
//                if (userInfo.getName() != null)
//                    name.setText(userInfo.getName());
//                if (userInfo.getPhone() != null)
//                    phone.setText(userInfo.getPhone());
//                if (userInfo.getBirthday() != null)
//                    date.setText(userInfo.getBirthday());
//                if (userInfo.getSex() != null)
//                    sex.setText(userInfo.getSex());
//                if (userInfo.getAddress() != null)
//                    address.setText(userInfo.getAddress());
//            }
//        }
//        mInfoDataSource.close();
//    }
//}