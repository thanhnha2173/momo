package com.example.do_an.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.do_an.ui.ChiTietGoiDataActivity;

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
        setupRecycleView();

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        return view;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuCollection menuCollection);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    private void setupRecycleView() {
        listChooseCollection();
        DataAdapter adapter = new DataAdapter(lstData, requireContext(), new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuCollection menuCollection, String title) {
                Intent intent = new Intent(requireContext(), ChiTietGoiDataActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
    private void listChooseCollection() {
        lstData = new ArrayList<>();
        lstData.add(new MenuCollection(1, "70.000 Đ", R.drawable.k70));
        lstData.add(new MenuCollection(2, "90.000 Đ", R.drawable.k90));
        lstData.add(new MenuCollection(3, "120.000 Đ", R.drawable.k120));
        lstData.add(new MenuCollection(4, "30.000 Đ", R.drawable.k30));
        lstData.add(new MenuCollection(5, "10.000 Đ", R.drawable.k10));
        lstData.add(new MenuCollection(6, "5.000 Đ", R.drawable.k5));
        lstData.add(new MenuCollection(7, "3.000 Đ", R.drawable.k3));
        lstData.add(new MenuCollection(8, "15.000 Đ", R.drawable.k15));
    }
}

