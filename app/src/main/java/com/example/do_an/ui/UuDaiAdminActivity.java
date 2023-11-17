package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.do_an.R;
import com.example.do_an.adapter.UuDaiAdminAdapter;
import com.example.do_an.model.UuDaiModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UuDaiAdminActivity extends AppCompatActivity {
    private FloatingActionButton fabAddUuDai;
    private RecyclerView recyclerView;
    private ImageButton back_uudai_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uu_dai_admin);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fabAddUuDai = findViewById(R.id.fab_add_uudai);
        recyclerView = findViewById(R.id.recycle_ud_admin);
        back_uudai_admin = findViewById(R.id.back_uudai_admin);
        displayUuDaiListFromFirestore();

        fabAddUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddUuDaiDialog();
            }
        });
    }
    private void displayUuDaiListFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference uuDaiRef = db.collection("uuDai");

        uuDaiRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<UuDaiModel> uuDaiList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    if (documentSnapshot.exists()) {
                        UuDaiModel uuDai = documentSnapshot.toObject(UuDaiModel.class);
                        uuDaiList.add(uuDai);
                    }
                }

                // Sau khi có dữ liệu từ Firestore, cập nhật RecyclerView
                updateRecyclerView(uuDaiList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Xử lý khi lấy dữ liệu từ Firestore thất bại
            }
        });
    }

    private void updateRecyclerView(List<UuDaiModel> uuDaiList) {
        // Khởi tạo adapter và gắn vào RecyclerView
        UuDaiAdminAdapter uuDaiAdminAdapter = new UuDaiAdminAdapter(uuDaiList, this);
        recyclerView.setAdapter(uuDaiAdminAdapter);
    }

    private void openAddUuDaiDialog() {
        // Sử dụng AlertDialog hoặc custom layout để tạo form nhập liệu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_uudai, null);
        builder.setView(dialogView);

        EditText tenUuDaiEditText = dialogView.findViewById(R.id.edit_text_ten_uudai);
        EditText moTaEditText = dialogView.findViewById(R.id.edit_text_mo_ta);
        EditText urlEditText = dialogView.findViewById(R.id.Url);
        Button xacNhanButton = dialogView.findViewById(R.id.button_xac_nhan);

        AlertDialog dialog = builder.create();
        dialog.show();

        xacNhanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenUuDai = tenUuDaiEditText.getText().toString();
                String moTa = moTaEditText.getText().toString();
                String imageUrl = urlEditText.getText().toString();

                if (!tenUuDai.isEmpty() && !moTa.isEmpty() && !imageUrl.isEmpty()) {
                    // Tạo mới UuDaiModel với thông tin nhập từ form
                    UuDaiModel newUuDai = new UuDaiModel(tenUuDai, moTa, imageUrl);

                    // Đẩy thông tin lên Firestore
                    addToFirestore(newUuDai);
                } else {
                    // Xử lý khi các trường thông tin cần nhập chưa được điền đầy đủ
                    // Có thể hiển thị thông báo lỗi hoặc yêu cầu điền đầy đủ thông tin
                }
                dialog.dismiss();
            }
        });
    }
    private void addToFirestore(UuDaiModel uuDaiModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference uuDaiRef = db.collection("uuDai");

        // Tạo một Map chứa thông tin của uuDaiModel để đẩy lên Firestore
        Map<String, Object> uuDaiData = new HashMap<>();
        uuDaiData.put("tenUuDai", uuDaiModel.getTenUuDai());
        uuDaiData.put("thongTinChiTiet", uuDaiModel.getThongTinChiTiet());
        uuDaiData.put("imageUrl", uuDaiModel.getImageUrl());

        // Thêm dữ liệu vào Firestore
        uuDaiRef.add(uuDaiData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Xử lý khi dữ liệu được thêm thành công
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi việc thêm dữ liệu thất bại
                    }
                });
    }
}