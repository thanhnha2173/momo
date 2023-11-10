package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.do_an.R;
import com.example.do_an.adapter.ThongBaoAdapter;
import com.example.do_an.model.ThongBaoModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThongbaoActivity extends AppCompatActivity {
    private static final String TAG = "ThongbaoActivity";
    RecyclerView recyclerView;
    ImageButton btnNofBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);
        btnNofBack = findViewById(R.id.btnNofBack);
        recyclerView = findViewById(R.id.recyclethongbao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        btnNofBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TransactionInfo")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ThongBaoModel> thongBaoList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getString("iddata");
                            String price = document.getString("pricetran");
                            String date = document.getString("date");
                            String hour = document.getString("hour");
                            ThongBaoModel thongBao = new ThongBaoModel(id, price, date, hour);
                            thongBaoList.add(thongBao);
                        }
                        Collections.sort(thongBaoList, new Comparator<ThongBaoModel>() {
                            @Override
                            public int compare(ThongBaoModel o1, ThongBaoModel o2) {
                                int dateComparison = o2.getDate().compareTo(o1.getDate());
                                if (dateComparison == 0) {
                                    return o2.getHour().compareTo(o1.getHour());
                                }
                                return dateComparison;
                            }
                        });

                        // Cập nhật RecyclerView thông qua Adapter
                        ThongBaoAdapter adapter = new ThongBaoAdapter(thongBaoList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}