package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.fragment.DataFragment;
import com.example.do_an.fragment.HomeFragment;
import com.example.do_an.model.MenuCollection;

public class PaymentSuccessActivity extends AppCompatActivity {
    private Button btnAnotherBuy;
    private ImageButton btnBackHome;
    private MenuCollection menuCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        btnBackHome = findViewById(R.id.btnBackHome);
        btnAnotherBuy = findViewById(R.id.btnAnotherBuy);
        String dataprice = getIntent().getStringExtra("dataprice");
        TextView datapricecon = findViewById(R.id.datapricecon);
        datapricecon.setText(dataprice);

        menuCollection = getIntent().getParcelableExtra("menuCollection");
        btnAnotherBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccessActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
}