package com.example.do_an.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.adapter.ThongBaoAdapter;
import com.example.do_an.adapter.TransHisAdapter;
import com.example.do_an.model.ThongBaoModel;
import com.example.do_an.ui.ThongkeActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TransHisFragment extends Fragment {
    private static final String TAG = "TransactionHistoryFragment";
    private RecyclerView recyclerView;
    private List<ThongBaoModel> transactionList;
    private TextView report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_his, container, false);

        report = view.findViewById(R.id.report);
        recyclerView = view.findViewById(R.id.recycle_transhis);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionList = new ArrayList<>();

        fetchDataFromFirestore();

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongkeActivity.class);
                startActivity(intent);
            }
        });

        return view;
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
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
        });
    }

    public TransHisFragment() {
        super();
    }
}
