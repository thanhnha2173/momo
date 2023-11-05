package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.fragment.DataFragment;
import com.example.do_an.fragment.HomeFragment;
import com.example.do_an.model.MenuCollection;

public class PaymentSuccessActivity extends AppCompatActivity {
    private Button btnBuyData;
    private ImageButton btnBackHome;
    private MenuCollection menuCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        btnBackHome = findViewById(R.id.btnBackHome);
        btnBuyData = findViewById(R.id.btnBuyData);
        String dataprice = getIntent().getStringExtra("dataprice");
        TextView datapricecon = findViewById(R.id.datapricecon);
        datapricecon.setText(dataprice);

        menuCollection = getIntent().getParcelableExtra("menuCollection");
        btnBuyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


    }
}