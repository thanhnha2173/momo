//package com.example.do_an.fragment;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.text.InputType;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.do_an.R;
//import com.example.do_an.database.UserDataSource;
//import com.example.do_an.model.User;
//
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * create an instance of this fragment.
// */
//public class ChangPassFragment extends Fragment {
//
//    ImageButton imbackChangPass;
//    EditText edcurPass, ednewPass;
//  //  Drawable drawable = getResources().getDrawable(R.drawable.round_check_24);
//
//    ImageView ivSHPassCur, ivSHPassNew;
//    TextView dk8kitu, dk1so, dkHT, dktruePasscur;
//    Button checknewPass, btChangePass;
//    private UserDataSource mDataSource;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_chang_pass, container, false);
//        mDataSource = new UserDataSource(getContext());
//        mDataSource.open();
//        edcurPass = view.findViewById(R.id.edpassCur);
//        imbackChangPass = view.findViewById(R.id.imbbackFraChLa);
//        ednewPass = view.findViewById(R.id.edpassNew);
//        ivSHPassCur = view.findViewById(R.id.ivSHPassCur);
//        ivSHPassNew = view.findViewById(R.id.ivSHPassNew);
//        dk8kitu = view.findViewById(R.id.dk8kitu);
//        dk1so = view.findViewById(R.id.dkso);
//        dkHT = view.findViewById(R.id.dkchuhoavath);
//        dktruePasscur = view.findViewById(R.id.dktruePasscur);
//        btChangePass = view.findViewById(R.id.btchangePass);
//        checknewPass = view.findViewById(R.id.checknewPass);
//        ivSHPassCur.setImageResource(R.drawable.hidepass);
//        ivSHPassNew.setImageResource(R.drawable.hidepass);
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String passwordCurr = sharedPreferences.getString("passworD", "");
//        String username = sharedPreferences.getString("usernamE", "");
//
//        btChangePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String text = ednewPass.getText().toString();
//                String text2 = edcurPass.getText().toString();
//                if(text.length() >= 8 && text.matches(".*\\d+.*") && text.matches("(?=.*[A-Z])(?=.*[a-z]).+") && passwordCurr.equals(text2))
//                {
//                    List<User> users = mDataSource.getAllUsers();
//                    for (User user : users) {
//                        if (user.getUsername().equals(username))
//                            mDataSource.updatePassword(user, text);
//                    }
//                    mDataSource.close();
//                    Toast myToast = Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT);
//                    myToast.show();
//                    getFragmentManager().popBackStack();
//                }
//                else
//                {
//                    Toast myToast = Toast.makeText(getContext(), "Chưa thoả mãn các điều kiện", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//            }
//        });
//
//        imbackChangPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().popBackStack();
//            }
//        });
//
//        ivSHPassCur.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                togglePasswordVisibility(edcurPass, ivSHPassCur);
//            }
//        });
//
//        ivSHPassCur.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    ivSHPassCur.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    ivSHPassCur.clearColorFilter();
//                }
//                return false;
//            }
//        });
//
//
//        ivSHPassNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                togglePasswordVisibility(ednewPass, ivSHPassNew);
//            }
//        });
//
//        ivSHPassNew.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    ivSHPassNew.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.SRC_ATOP);
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    ivSHPassNew.clearColorFilter();
//                }
//                return false;
//            }
//        });
//        checknewPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkPass(passwordCurr);
//            }
//        });
//        return view;
//    }
//
//    private void togglePasswordVisibility(EditText password, ImageView showPassword) {
//        int inputType = password.getInputType();
//        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            showPassword.setImageResource(R.drawable.hidepass);
//        } else {
//            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            showPassword.setImageResource(R.drawable.showpass);
//        }
//        password.setSelection(password.length());
//    }
//    private void checkPass(String passwordCurr)
//    {
//        String text = ednewPass.getText().toString();
//        String text2 = edcurPass.getText().toString();
//        Drawable drawable = getResources().getDrawable(R.drawable.round_check_24);
//        Drawable drawable2 = getResources().getDrawable(R.drawable.baseline_close_24);
//        dk8kitu.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//        dk1so.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//        dkHT.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//        dktruePasscur.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//        if (passwordCurr.equals(text2))
//            dktruePasscur.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        else
//            dktruePasscur.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//        if (text.length() >= 8)
//            dk8kitu.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        else
//            dk8kitu.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//        if (text.matches(".*\\d+.*"))
//            dk1so.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        else
//            dk1so.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//        if (text.matches("(?=.*[A-Z])(?=.*[a-z]).+"))
//            dkHT.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//        else
//            dkHT.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//    }
//
//}