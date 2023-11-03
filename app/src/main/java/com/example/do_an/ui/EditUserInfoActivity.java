//package com.example.do_an.ui;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//
//import com.example.do_an.R;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.admin.InfoUserActivityAdmin;
//import com.example.do_an.model.UserInfo;
//
//import java.util.Calendar;
//import java.util.List;
//
//public class EditUserInfoActivity extends AppCompatActivity {
//    ImageButton imbbackInfoEdit;
//    TextView cannel;
//    EditText nameedit, dateedit, sexedit, addressedit;
//    Button save;
//    private UserInfoDataSource mInfoDataSource;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_user_info);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferences2 = getSharedPreferences("my_check", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        Log.d("a1", username);
//        int a = 0;
//        String usernameCheck = sharedPreferences2.getString("checKphone", "");
//        if (username.equals("1111")) {
//            username = usernameCheck;
//            a = 1;
//        }
//        Log.d("a2", username);
//        imbbackInfoEdit = findViewById(R.id.imbbackInfoEdit);
//        cannel = findViewById(R.id.cannel);
//        nameedit = findViewById(R.id.nameedit);
//        dateedit = findViewById(R.id.dateedit);
//        sexedit = findViewById(R.id.sexedit);
//        addressedit = findViewById(R.id.addressedit);
//        save = findViewById(R.id.save);
//        setTV(username);
//        dateedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });
//        imbbackInfoEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        cannel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        String finalUsername = username;
//        int finalA = a;
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog(finalUsername, finalA);
//            }
//        });
//    }
//
//    private void showDatePickerDialog() {
//        // Lấy ngày hiện tại
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//
//        // Tạo hộp thoại DatePickerDialog
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Xử lý sự kiện khi người dùng chọn ngày
//                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
//                        dateedit.setText(date);
//                    }
//                }, year, month, dayOfMonth);
//
//        // Hiển thị hộp thoại DatePickerDialog
//        datePickerDialog.show();
//    }
//
//    private void showDialog(String username, int a) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Chỉnh sửa thành công");
//        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Hành động khi người dùng chọn Đồng ý
//                finish();
//                updateUserInfo(username, nameedit.getText().toString(), dateedit.getText().toString(), sexedit.getText().toString(), addressedit.getText().toString()); // Lưu thông tin người dùng vào bảng InfoUser
//                Intent intent = new Intent(EditUserInfoActivity.this, InfoUserActivity.class);
//                Intent intent2 = new Intent(EditUserInfoActivity.this, InfoUserActivityAdmin.class);
//                if (a == 1)
//                    startActivity(intent2);
//                else
//                    startActivity(intent);
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
////    private void setTV(String username) {
////        mInfoDataSource = new UserInfoDataSource(this);
////        mInfoDataSource.open();
////        List<UserInfo> userInfos = mInfoDataSource.getAllUserInfos();
////        for (UserInfo userInfo : userInfos) {
////            if (userInfo.getPhone().equals(username) && userInfo.getName() != null) {
////                if (!userInfo.getName().equals("Chưa thiết lập"))
////                    nameedit.setText(userInfo.getName());
////                if (!userInfo.getBirthday().equals("Chưa thiết lập"))
////                    dateedit.setText(userInfo.getBirthday());
////                if (!userInfo.getSex().equals("Chưa thiết lập"))
////                    sexedit.setText(userInfo.getSex());
////                if (!userInfo.getAddress().equals("Chưa thiết lập"))
////                    addressedit.setText(userInfo.getAddress());
////            }
////        }
////        mInfoDataSource.close();
////    }
////
////    public void updateUserInfo(String username, String name, String birthday, String sex, String address) {
////        //SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
////        //String username = sharedPreferences.getString("usernamE", "");
////        mInfoDataSource = new UserInfoDataSource(this);
////        mInfoDataSource.open();
////        List<UserInfo> userInfos = mInfoDataSource.getAllUserInfos();
////        for (UserInfo userInfo : userInfos) {
////            if (userInfo.getPhone().equals(username)) {
////                mInfoDataSource.updateUserInfo(userInfo, name, birthday, sex, address);
////                if (name != null)
////                    namefullC.setText(userInfo.getName());
////                if (birthday != null)
////                    dateC.setText(userInfo.getBirthday());
////                if (sex != null)
////                    sexC.setText(userInfo.getSex());
////                if (address != null)
////                    addressC.setText(userInfo.getAddress());
//            }
//        }
//
//        mInfoDataSource.close();
//
//    }
//
//
//
//    public void showPopupMenu(View v) {
//        PopupMenu popupMenu = new PopupMenu(this, v);
//        popupMenu.getMenuInflater().inflate(R.menu.popmenu, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                String gender = item.getTitle().toString();
//                EditText genderEditText = findViewById(R.id.sexedit);
//                genderEditText.setText(gender);
//                return true;
//            }
//        });
//        popupMenu.show();
//    }
//
//}