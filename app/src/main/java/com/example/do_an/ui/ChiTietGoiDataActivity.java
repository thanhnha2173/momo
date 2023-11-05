package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.do_an.R;
import com.example.do_an.model.MenuCollection;

public class ChiTietGoiDataActivity extends AppCompatActivity {

    private ImageButton dedataback;
    private Button btbBuyData;
    private MenuCollection menuCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_goi_data);
        dedataback = findViewById(R.id.dedataback);
        btbBuyData = findViewById(R.id.btnBuyData);

        btbBuyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietGoiDataActivity.this, PaymentSuccessActivity.class);

                intent.putExtra("menuCollection", menuCollection);

                startActivity(intent);
            }
        });

        dedataback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MenuCollection menuCollection = getIntent().getParcelableExtra("menuCollection");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}