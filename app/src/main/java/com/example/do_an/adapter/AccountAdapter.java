package com.example.do_an.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.model.AccountModel;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(AccountModel account);
    }
    private List<AccountModel> accounts;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tk_item, parent, false);
        return new AccountViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        AccountModel account = accounts.get(position);
        holder.bind(account);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(account);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts != null ? accounts.size() : 0;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountNameTextView;

        public AccountViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.stk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(accounts.get(position));
                    }
                }
            });
        }

        public void bind(AccountModel account) {
            String maTTCN = account.getMaTTCN();
            String chiSo = maTTCN.replaceAll("[^0-9]", "");
            accountNameTextView.setText(chiSo);
        }
    }
}
