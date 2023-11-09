package com.example.do_an.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.model.ThongBaoModel;
import com.example.do_an.model.UserInfo;
import com.example.do_an.ui.ChuyenTienActivity;
import com.example.do_an.ui.DataActivity;
import com.example.do_an.ui.EnterSdtActivity;
import com.example.do_an.ui.NapTienActivity;
import com.example.do_an.ui.PersonalPageActivity;
import com.example.do_an.ui.RutTienActivity;
import com.example.do_an.ui.ThongbaoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.example.do_an.ui.InfoUserActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ImageButton goUsers, thongbao;
    TextView search, soduvi;
    LinearLayout naptien, rutTien, chuyenTien, napdata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        goUsers = view.findViewById(R.id.goUsers);
        soduvi = view.findViewById(R.id.soduvi);
        naptien = view.findViewById(R.id.naptien);
        rutTien = view.findViewById(R.id.rutTien);
        chuyenTien = view.findViewById(R.id.chuyenTien);
        search = view.findViewById(R.id.search);
        napdata = view.findViewById(R.id.napdata);
        thongbao = view.findViewById(R.id.thongbao);
        getInfo(phoneNumber);
        naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NapTienActivity.class);
                startActivity(intent);
            }
        });
        rutTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RutTienActivity.class);
                startActivity(intent);
            }
        });
        chuyenTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChuyenTienActivity.class);
                startActivity(intent);
            }
        });
        thongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThongbaoActivity.class);
                startActivity(intent);
            }
        });
        napdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataActivity.class);
                startActivity(intent);
            }
        });
        goUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    void getInfo(String phoneNumber){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                long sodu = document.getLong("soDuVi");
                                if(sodu >= 1000)
                                    soduvi.setText("Số dư ví: " + String.format("%,d", sodu) + "đ"); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                                else
                                    soduvi.setText(String.valueOf(sodu)); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn

                            }
                        }
                    }
                });
    }
    public HomeFragment() {
        super();
    }
}