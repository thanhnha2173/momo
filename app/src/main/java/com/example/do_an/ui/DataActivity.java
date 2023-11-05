package com.example.do_an.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.adapter.DataAdapter;
import com.example.do_an.fragment.DataFragment;
import com.example.do_an.model.MenuCollection;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    private ImageButton btnBack;

    private  RecyclerView recycle_data;
    private  DataAdapter mnCollectionAdapter;
    private List<MenuCollection> lstData;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataFragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void anhXa(View view){
        recycle_data = view.findViewById(R.id.recycle_data);
    }
}