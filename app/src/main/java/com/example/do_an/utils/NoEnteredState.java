package com.example.do_an.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.do_an.ui.NapTienActivity;

public class NoEnteredState implements PaymentState {
    @Override
    public void handleInput(NapTienActivity activity){
        String soduviNTText = activity.getSoduviNT().getText().toString().trim();
        if (TextUtils.isEmpty(soduviNTText)){
            Toast.makeText(activity, "Chưa nhập số tiền cần nạp", Toast.LENGTH_SHORT).show();
        } else {
            activity.setCurrentState(new EnteredState()); // Chuyển trạng thái sang EnteredState
        }
    }
}


