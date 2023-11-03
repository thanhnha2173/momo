//package com.example.do_an.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.ui.DatLichActivity;
//import com.example.do_an.ui.LoginActivity;
//
///**
// * A simple {@link Fragment} subclass.
// * create an instance of this fragment.
// */
//public class LocalFragment extends Fragment {
//
//    LinearLayout linearLayout1;
//    LinearLayout linearLayout2;
//    LinearLayout linearLayout3;
//    LinearLayout linearLayout4;
//    TextView tv1, tv2, tv3, tv4;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_local, container, false);
//        linearLayout1 = view.findViewById(R.id.LLlocal1);
//        linearLayout2 = view.findViewById(R.id.LLlocal2);
//        linearLayout3 = view.findViewById(R.id.LLlocal3);
//        linearLayout4 = view.findViewById(R.id.LLlocal4);
//        tv1 = view.findViewById(R.id.tv1);
//        tv2 = view.findViewById(R.id.tv2);
//        tv3 = view.findViewById(R.id.tv3);
//        tv4 = view.findViewById(R.id.tv4);
//// Tạo một listener
//        View.OnClickListener linearLayoutClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Lấy ID của LinearLayout được click
//                Intent intent = new Intent(getActivity(), DatLichActivity.class);
//                int id = view.getId();
//                // Xử lý sự kiện tại đây dựa trên ID của LinearLayout được click
//                switch (id) {
//                    case R.id.LLlocal1:
//                        intent.putExtra("key", tv1.getText().toString());
//                        startActivity(intent);
//                        break;
//                    case R.id.LLlocal2:
//                        intent.putExtra("key", tv2.getText().toString());
//                        startActivity(intent);
//                        break;
//                    case R.id.LLlocal3:
//                        // Xử lý sự kiện cho LinearLayout 3
//                        intent.putExtra("key", tv3.getText().toString());
//                        startActivity(intent);
//                        break;
//                    case R.id.LLlocal4:
//                        intent.putExtra("key", tv4.getText().toString());
//                        // Xử lý sự kiện cho LinearLayout 4
//                        startActivity(intent);
//                        break;
//                }
//
//            }
//        };
//
//// Đăng ký listener cho các LinearLayout
//        linearLayout1.setOnClickListener(linearLayoutClickListener);
//        linearLayout2.setOnClickListener(linearLayoutClickListener);
//        linearLayout3.setOnClickListener(linearLayoutClickListener);
//        linearLayout4.setOnClickListener(linearLayoutClickListener);
//
//        return view;
//    }
//}