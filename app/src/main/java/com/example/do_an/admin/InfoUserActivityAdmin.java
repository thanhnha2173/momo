//package com.example.do_an.admin;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.do_an.R;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.model.UserInfo;
//import com.example.do_an.ui.EditUserInfoActivity;
//
//import java.util.List;
//
//public class InfoUserActivityAdmin extends AppCompatActivity {
//    TextView name, phone, date, sex, address, fixAdmin;
//    ImageButton imbackaduserAD;
//    private UserInfoDataSource mInfoDataSource;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info_user_admin);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        Intent intent = getIntent();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_check", Context.MODE_PRIVATE);
//        String usernameCheck = sharedPreferences.getString("checKphone", "");
//        String username = intent.getStringExtra("phoneUser");
//        if(username == null)
//            username = usernameCheck;
//        fixAdmin = findViewById(R.id.fixAdmin);
//        fixAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(InfoUserActivityAdmin.this, EditUserInfoActivity.class);
//                finish();
//                startActivity(i);
//            }
//        });
//        imbackaduserAD = findViewById(R.id.imbackaduserAD);
//        imbackaduserAD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        name = findViewById(R.id.nameAD);
//        phone = findViewById(R.id.phoneAD);
//        date = findViewById(R.id.dateAD);
//        sex = findViewById(R.id.sexAD);
//        address = findViewById(R.id.addressAD);
//        setTV(username);
//    }
//
//    private void setTV(String username) {
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
//
//
//}