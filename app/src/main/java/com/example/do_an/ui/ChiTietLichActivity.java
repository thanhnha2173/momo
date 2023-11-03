//package com.example.do_an.ui;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.admin.AllLichActivity;
//import com.example.do_an.model.HisCar;
//
//import java.util.List;
//
//public class ChiTietLichActivity extends AppCompatActivity {
//    ImageButton imbbackADcal;
//    TextView fixChitiet, nameCal, dateCal, statusCal, motaCal, sdtCal;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chi_tiet_lich);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        Intent intent = getIntent();
//        String username_datafix = intent.getStringExtra("id");
//        Log.d("123", username_datafix);
//        String username;
//        String datefix;
//        String[] arr = username_datafix.split("-");
//        if (arr.length >= 2) {
//            username = arr[0];
//            datefix = arr[1];
//            Log.d("check1", username + " " + datefix);
//            imbbackADcal = findViewById(R.id.imbbackADcal);
//            fixChitiet = findViewById(R.id.fixChitiet);
//            fixChitiet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(ChiTietLichActivity.this, EditChiTietLichActivity.class);
//                    intent.putExtra("phoneDel", sdtCal.getText().toString());
//                    startActivity(intent);
//                    //finish();
//                }
//            });
//            nameCal = findViewById(R.id.nameCal);
//            statusCal = findViewById(R.id.statusCal);
//            dateCal = findViewById(R.id.dateCal);
//            motaCal = findViewById(R.id.motaCal);
//            sdtCal = findViewById(R.id.sdtCal);
//            imbbackADcal.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(ChiTietLichActivity.this, AllLichActivity.class);
//                    Intent intent2 = new Intent(ChiTietLichActivity.this, MainActivity.class);
//                    SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//                    String admin = sharedPreferences.getString("usernamE", "");
//                    if (admin.equals("1111"))
//                        startActivity(intent);
//                    else
//                        startActivity(intent2);
//                    finish();
//                }
//            });
//            setTV(username, datefix);
//        }
//    }
//
//    private void setTV(String username, String datefix) {
//        CarHisDataSource carHisDataSource = new CarHisDataSource(this);
//        carHisDataSource.open();
//        List<HisCar> hisCars = carHisDataSource.getAllHisCar(username);
//        for (HisCar hisCar : hisCars) {
//            if (hisCar.getHisuser().equals(username) && hisCar.getDateFix().equals(datefix)) {
//                nameCal.setText(hisCar.getNameLocal());
//                dateCal.setText(hisCar.getDateFix());
//                statusCal.setText(hisCar.getStatus());
//                motaCal.setText(hisCar.getMota());
//                sdtCal.setText(hisCar.getHisuser());
//            }
//        }
//        carHisDataSource.close();
//    }
//};
//
//
