package com.example.do_an.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.adapter.DataAdapter;
import com.example.do_an.model.MenuCollection;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class DataFragment extends Fragment {
    private RecyclerView recycle_data, recyclerView;
    private DataAdapter mnCollectionAdapter;
    private List<MenuCollection> lstData;
    private ImageButton btnBack1;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        btnBack1 = view.findViewById(R.id.btnBack1);
        recyclerView = view.findViewById(R.id.recyclerView);
        recycle_data = view.findViewById(R.id.recycle_data);
        listChooseCollection();

        mnCollectionAdapter = new DataAdapter(lstData, requireContext());
        recyclerView.setAdapter(mnCollectionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        return view;
    }


    private void listChooseCollection() {
        lstData = new ArrayList<>();
        lstData.add(new MenuCollection(1, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(2, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(3, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(4, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(5, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(6, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(7, "70.000 Đ", R.drawable.momoimg));
        lstData.add(new MenuCollection(8, "70.000 Đ", R.drawable.momoimg));
    }
}

