package com.example.do_an.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.model.UuDaiModel;
import com.example.do_an.ui.UuDaiAdminActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UuDaiAdminAdapter extends RecyclerView.Adapter<UuDaiAdminAdapter.UuDaiViewHolder> {
    private List<UuDaiModel> uuDaiList;
    private Context context;

    public UuDaiAdminAdapter(List<UuDaiModel> uuDaiList, Context context) {
        this.uuDaiList = uuDaiList;
        this.context = context;
    }
    @NonNull
    @Override
    public UuDaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uudai_admin, parent, false);
        return new UuDaiViewHolder(view);
    }
    public static class UuDaiViewHolder extends RecyclerView.ViewHolder {
        TextView titleUuDaiTextView;
        TextView descriptionUuDaiTextView;
        ImageView imageUuDaiImageView;

        public UuDaiViewHolder(@NonNull View itemView) {
            super(itemView);
            titleUuDaiTextView = itemView.findViewById(R.id.title_ud_admin);
            descriptionUuDaiTextView = itemView.findViewById(R.id.description_ud_admin);
            imageUuDaiImageView = itemView.findViewById(R.id.img_ud_admin);
        }
    }
    public void addUuDai(UuDaiModel newUuDai) {
        uuDaiList.add(newUuDai);
        notifyItemInserted(uuDaiList.size() - 1); // Thông báo cho RecyclerView biết rằng có dữ liệu mới được thêm vào vị trí cuối cùng
    }

    @Override
    public void onBindViewHolder(@NonNull UuDaiViewHolder holder, int position) {
        UuDaiModel uuDai = uuDaiList.get(position);

        holder.titleUuDaiTextView.setText(uuDai.getTenUuDai());
        holder.descriptionUuDaiTextView.setText(uuDai.getThongTinChiTiet());

        // Set text mới thay vì load hình ảnh từ URL bằng Picasso
        Picasso.get().load(uuDai.getImageUrl()).placeholder(R.drawable.momoimg).into(holder.imageUuDaiImageView);
    }

    @Override
    public int getItemCount() {
        return uuDaiList.size();
    }
    public void setData(List<UuDaiModel> uuDaiList) {
        this.uuDaiList = uuDaiList;
        notifyDataSetChanged(); // Thông báo cho RecyclerView biết dữ liệu đã thay đổi
    }

}
