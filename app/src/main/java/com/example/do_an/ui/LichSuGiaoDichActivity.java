package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.do_an.R;
import com.example.do_an.adapter.TransHisAdapter;
import com.example.do_an.model.ThongBaoModel;
import com.example.do_an.model.TransactionInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LichSuGiaoDichActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransHisAdapter adapter;
    private FirebaseFirestore db;
    private List<ThongBaoModel> transactionList;
    private ImageButton bck_lsgd_admin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_giao_dich);

        bck_lsgd_admin = findViewById(R.id.bck_lsgd_admin);
        recyclerView = findViewById(R.id.recycle_lsgd_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bck_lsgd_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transactionList = new ArrayList<>();
        adapter = new TransHisAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fetchDataFromFirestore();
    }

    private void fetchDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TransactionInfo").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getString("iddata");
                            String amount = document.getString("pricetran");
                            String date = document.getString("date");
                            String time = document.getString("hour");
                            ThongBaoModel transaction = new ThongBaoModel(id, amount, date, time);
                            transactionList.add(transaction);
                        }

                        Collections.sort(transactionList, new Comparator<ThongBaoModel>() {
                            @Override
                            public int compare(ThongBaoModel o1, ThongBaoModel o2) {
                                int dateComparison = o2.getDate().compareTo(o1.getDate());

                                if (dateComparison == 0) {
                                    return o2.getHour().compareTo(o1.getHour());
                                }
                                return dateComparison;
                            }
                        });
                        TransHisAdapter adapter = new TransHisAdapter(transactionList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                    }
                });
    }
}