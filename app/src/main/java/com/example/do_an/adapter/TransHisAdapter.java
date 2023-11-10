package com.example.do_an.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.model.ThongBaoModel;

import java.util.List;

public class TransHisAdapter extends RecyclerView.Adapter<TransHisAdapter.ViewHolder> {
    private List<ThongBaoModel> transactionList;

    public TransHisAdapter(List<ThongBaoModel> transactionList) {
        this.transactionList = transactionList;
    }
    @NonNull
    @Override
    public TransHisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transhis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransHisAdapter.ViewHolder holder, int position) {
        ThongBaoModel transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titletran, pricetran, date, hour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletran = itemView.findViewById(R.id.title_transhis);
            pricetran = itemView.findViewById(R.id.tien_transhis);
            date = itemView.findViewById(R.id.ngay_transhis);
            hour = itemView.findViewById(R.id.gio_transhis);
        }
        public void bind(ThongBaoModel transaction) {
            titletran.setText(transaction.getTitle() + " thành công");
            pricetran.setText(transaction.getPrice());
            date.setText(transaction.getDate());
            hour.setText(transaction.getHour());
        }
    }
}
