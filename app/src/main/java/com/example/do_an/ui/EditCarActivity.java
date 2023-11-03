//package com.example.do_an.ui;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.example.do_an.database.CarDataSource;
////import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.model.CarInfo;
//
//import java.util.List;
//
//public class EditCarActivity extends AppCompatActivity {
//    EditText ednamecar, edbienso, eddtxl;
//    ImageButton imbbackEditCar;
//    TextView cannelCar;
//    Button saveCar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_car);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        ednamecar = findViewById(R.id.ednamecar);
//        edbienso = findViewById(R.id.edbienso);
//        eddtxl = findViewById(R.id.eddtxl);
//        imbbackEditCar = findViewById(R.id.imbbackEditCar);
//        cannelCar = findViewById(R.id.cannelCar);
//        saveCar = findViewById(R.id.saveCar);
//        setTV(username);
//        imbbackEditCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        cannelCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        saveCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateCar(ednamecar.getText().toString(), edbienso.getText().toString(), eddtxl.getText().toString(), username); // Lưu thông tin người dùng vào bảng InfoUser
//                Intent intent = new Intent(EditCarActivity.this, MainActivity.class);
//                intent.putExtra("fragment", "mycar");
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    private void setTV(String username) {
//        CarDataSource carDataSource = new CarDataSource(this);
//        carDataSource.open();
//        List<CarInfo> carInfos = carDataSource.getAllCar();
//        for (CarInfo carInfo : carInfos) {
//            if (carInfo.getCaruser().equals(username)) {
//                if (carInfo.getNamecar() != null)
//                    ednamecar.setText(carInfo.getNamecar());
//                if (carInfo.getBienso() != null)
//                    edbienso.setText(carInfo.getBienso());
//                if (carInfo.getDtxl() != null)
//                    eddtxl.setText(carInfo.getDtxl());
//            }
//
//        }
//        carDataSource.close();
//    }
//    public void updateCar(String namecar, String bienso, String dtxl, String carusser)
//    {
//        CarDataSource carDataSource = new CarDataSource(this);
//        carDataSource.open();
//        List<CarInfo> carInfos = carDataSource.getAllCar();
//        for (CarInfo carInfo : carInfos) {
//            if (carInfo.getCaruser().equals(carusser)) {
//                carDataSource.updateCar(carInfo, namecar, bienso, dtxl, carusser);
//            }
//        }
//        carDataSource.close();
//    }
//}