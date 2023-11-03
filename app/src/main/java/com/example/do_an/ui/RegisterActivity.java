package com.example.do_an.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.do_an.R;
import com.example.do_an.database.UserDataSource;
import com.example.do_an.model.User;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imbbackRE;
    EditText edpassRECon;
    Button btdkyctn;
    TextView tv1;
    private UserDataSource mDataSource;
    String check = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        if(getIntent().getStringExtra("check2") == null)
            check = getIntent().getStringExtra("check");
        Log.d("a", check); // Debug log

        imbbackRE = findViewById(R.id.imbbackRECon1);
        edpassRECon = findViewById(R.id.edpassRECon1);
        tv1 = findViewById(R.id.tvsdt);
        tv1.setText(phoneNumber);
        btdkyctn = findViewById(R.id.btdnlogin);
        mDataSource = new UserDataSource(this);
        mDataSource.open();

        imbbackRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btdkyctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edpassRECon.getText().toString();
                Log.d("TAG", otp); // Debug log
                if (!otp.equals("1234")) {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Mã OTP không hợp lệ", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else
                {
                    Intent intent;
                    if(!check.equals("1")){
                        intent = new Intent(RegisterActivity.this, Register2Activity.class);
                        Toast.makeText(RegisterActivity.this, "Vui lòng tạo mật khẩu để đăng ký tài khoản", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        intent = new Intent(RegisterActivity.this, QuenPassActivity.class);
                        Toast.makeText(RegisterActivity.this, "Vui lòng tạo lại mật khẩu để đăng nhập tài khoản", Toast.LENGTH_SHORT).show();

                    }
                    intent.putExtra("PHONE_NUMBER", phoneNumber);
                    startActivity(intent);

                }
        }});
    }
}