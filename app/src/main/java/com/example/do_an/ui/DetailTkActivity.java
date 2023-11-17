package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailTkActivity extends AppCompatActivity {
    TextView tentk_admin, sdt_admin;
    private FirebaseFirestore db;
    private String accountId;
    private Button btnkhoa_admin, btnmo_admin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tk);

        LinearLayout vi = findViewById(R.id.vi);
        LinearLayout ttcnadmin = findViewById(R.id.ttcn_admin);
        LinearLayout lsgdadmin = findViewById(R.id.lsgd_admin);
        tentk_admin = findViewById(R.id.tentk_admin);
        sdt_admin = findViewById(R.id.sdt_admin);
        btnkhoa_admin = findViewById(R.id.btnkhoa_admin);
        btnmo_admin = findViewById(R.id.btnmo_admin);

        db = FirebaseFirestore.getInstance();

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTkActivity.this, SoDuViActivity.class);
                startActivity(intent);
            }
        });
        ttcnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTkActivity.this, ThongTinCaNhanActivity.class);
                intent.putExtra("ACCOUNT_ID", accountId); // accountId là một String
                startActivity(intent);
            }
        });
        lsgdadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTkActivity.this, LichSuGiaoDichActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            accountId = intent.getStringExtra("ACCOUNT_ID");
            if (accountId != null) {
                // Khởi tạo Firestore và lấy thông tin chi tiết của tài khoản
                db = FirebaseFirestore.getInstance();
                getAccountDetails(accountId);
            }
        }

        btnkhoa_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị Dialog xác nhận trước khi khóa tài khoản
                showConfirmationDialog();
            }
        });
        btnmo_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị Dialog xác nhận trước khi mở khóa tài khoản
                showConfirmationDialogForUnlock();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    private void getAccountDetails(String accountId) {
        db.collection("UsersInfo").document(accountId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String hoTen = documentSnapshot.getString("HoTen");
                            String sdtWithCN = documentSnapshot.getString("MaTTCN");

                            String sdt = sdtWithCN.replace("CN", "");
                            // Hiển thị thông tin lên TextViews
                            tentk_admin.setText(hoTen);
                            sdt_admin.setText(sdt);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi không lấy được dữ liệu từ Firestore
                    }
                });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận khóa tài khoản");
        builder.setMessage("Bạn có chắc chắn muốn khóa tài khoản này?");

        // Nút Khóa
        builder.setPositiveButton("Khóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Gọi phương thức để khóa tài khoản
                lockAccount();
            }
        });

        // Nút Hủy
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng Dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị Dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void lockAccount(){
        String userAccountId = accountId.substring(2);
        // Đoạn mã để khóa tài khoản sau khi admin xác nhận
        // Ở đây, bạn có thể gọi các hàm cần thiết để thực hiện khóa tài khoản trong Firestore hoặc các tác vụ khác liên quan đến việc khóa tài khoản.
        db.collection("Users").document(userAccountId)
                .update("IsLocked", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Hiển thị thông báo cho admin khi tài khoản đã được khóa thành công
                        Toast.makeText(DetailTkActivity.this, "Tài khoản đã được khóa", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi không thể cập nhật trạng thái tài khoản
                        Toast.makeText(DetailTkActivity.this, "Khóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showConfirmationDialogForUnlock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận mở khóa tài khoản");
        builder.setMessage("Bạn có chắc chắn muốn mở khóa tài khoản này?");

        // Nút Mở khóa
        builder.setPositiveButton("Mở khóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Gọi phương thức để mở khóa tài khoản
                unlockAccount();
            }
        });

        // Nút Hủy
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng Dialog
                dialog.dismiss();
            }
        });

        // Tạo và hiển thị Dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void unlockAccount(){
        String userAccountId = accountId.substring(2);
        // Đoạn mã để mở khóa tài khoản sau khi admin xác nhận
        db.collection("Users").document(userAccountId)
                .update("IsLocked", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Hiển thị thông báo cho admin khi tài khoản đã được mở khóa thành công
                        Toast.makeText(DetailTkActivity.this, "Tài khoản đã được mở khóa", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi không thể cập nhật trạng thái tài khoản
                        Toast.makeText(DetailTkActivity.this, "Mở khóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}