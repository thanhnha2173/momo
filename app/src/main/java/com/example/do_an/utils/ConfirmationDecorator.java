package com.example.do_an.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.do_an.ui.NapTienActivity;

public class ConfirmationDecorator implements PaymentState, TransactionConfirmation{
    private PaymentState state;
    private NapTienActivity activity;

    public ConfirmationDecorator(PaymentState state, NapTienActivity activity) {
        this.state = state;
        this.activity = activity;
    }

    @Override
    public void handleInput(NapTienActivity activity) {
        // Thực hiện chức năng xác nhận giao dịch trước khi thực hiện hành động của trạng thái
        confirmTransaction();
    }

    @Override
    public void confirmTransaction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Xác nhận giao dịch");
        builder.setMessage("Bạn có chắc chắn muốn nạp tiền?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                // Thực hiện hành động của trạng thái sau khi xác nhận
                state.handleInput(activity);
            }
        });
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
