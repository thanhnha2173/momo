//package com.example.do_an.admin;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.do_an.R;
//import com.example.do_an.database.CarDataSource;
//import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.database.UserDataSource;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.model.User;
//
//import java.util.ArrayList;
//
//public class AllUsersActivity extends AppCompatActivity {
//    ImageView imbackaduser;
//    RecyclerView rvlistUser;
//    ArrayList<User> listUser;
//    UserAdapter userAdapter;
//    UserDataSource userDataSource;
//    UserInfoDataSource userInfoDataSource;
//    CarDataSource carDataSource;
//    CarHisDataSource carHisDataSource;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_users);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        imbackaduser = findViewById(R.id.imbackaduser);
//        rvlistUser = findViewById(R.id.rvlistUser);
//
//        imbackaduser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        userDataSource = new UserDataSource(this);
//        userDataSource.open();
//        userInfoDataSource = new UserInfoDataSource(this);
//        userInfoDataSource.open();
//        listUser = new ArrayList<>(userDataSource.getAllUsers());
//        userDataSource.close();
//        // Collections.sort(listUser, new HisCarDateComparator());
//        UserAdapter userAdapter = new UserAdapter(listUser, new UserAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(User user) {
//                Intent intent = new Intent(AllUsersActivity.this, InfoUserActivityAdmin.class);
//                intent.putExtra("phoneUser", user.getUsername());
//                // Khởi tạo đối tượng SharedPreferences
//                SharedPreferences sharedPreferences = getSharedPreferences("my_check", MODE_PRIVATE);
//                // Lưu thông tin username vào SharedPreferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("checKphone", user.getUsername()); // "username" là tên key, username là giá trị
//                editor.apply();
//                startActivity(intent);
//            }
//        }, this, rvlistUser);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rvlistUser.setLayoutManager(linearLayoutManager);
//        rvlistUser.setAdapter(userAdapter);
//    }
//}