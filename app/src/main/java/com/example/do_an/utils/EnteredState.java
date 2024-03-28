package com.example.do_an.utils;

import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.example.do_an.ui.NapTienActivity;

public class EnteredState implements PaymentState {
    @Override
    public void handleInput(NapTienActivity activity) {
        String soduviNTText = activity.getSoduviNT().getText().toString().trim();
        if (!TextUtils.isEmpty(soduviNTText)) {
            int sodu = Integer.parseInt(soduviNTText);
            if (sodu < 10000) {
                Toast.makeText(activity, "Nạp tối thiểu 10.000đ", Toast.LENGTH_SHORT).show();
            } else {
                // Tiếp tục xử lý logic nạp tiền
                activity.updateToFireStore(activity.getPhoneNumber(), sodu);
                activity.updateNotification(activity.getiddataNT().getText().toString().trim(), soduviNTText, activity.getCurrentDateAsString(), activity.getCurrentTime());
            }
        } else{
            // Nếu số tiền đủ điều kiện, chuyển trạng thái sang EnteredState
            activity.setCurrentState(new NoEnteredState());
        }
    }
}

