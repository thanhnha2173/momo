//package com.example.do_an.model;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.do_an.R;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class HisApdater extends RecyclerView.Adapter<HisApdater.UserViewHolder> {
//
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//        context = viewGroup.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View userView = inflater.inflate(R.layout.layout_carhis, viewGroup, false);
//        UserViewHolder viewHolder = new UserViewHolder(userView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
//        HisCar item = hisCars.get(i);
//        if(!checkDateTime(item.getDateFix()))
//        {
//            userViewHolder.tvStatus.setTextColor(Color.parseColor("#FF1C7A3B"));
//        }
//            userViewHolder.tvName.setText(item.getNameLocal());
//        userViewHolder.tvDataFix.setText(item.getDateFix());
//        userViewHolder.tvStatus.setText(item.getStatus());
//        userViewHolder.itemView.setOnClickListener(view -> userCallback.onItemClick(item.getHisuser() + "-" + item.getDateFix()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return hisCars.size();
//    }
//
//    class
//    UserViewHolder extends RecyclerView.ViewHolder {
//        TextView tvName;
//        TextView tvDataFix;
//        TextView tvStatus;
//
//        public UserViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvName = itemView.findViewById(R.id.tvNameLocal);
//            tvDataFix = itemView.findViewById(R.id.tvDataFix);
//            tvStatus = itemView.findViewById(R.id.tvStatus);
//
//        }
//    }
//
//    public interface UserCallback {
//        void onItemClick(String id);
//    }
//
//    private boolean checkDateTime(String date) {
//        String[] arrOfDate = date.split("/"); // Cắt chuỗi bởi dấu /
//        final Calendar c = Calendar.getInstance();
//        int currentYear = c.get(Calendar.YEAR);
//        int currentMonth = c.get(Calendar.MONTH) + 1;
//        int currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//        int selectedYear = Integer.valueOf(arrOfDate[2]);
//        int selectedMonth = Integer.valueOf(arrOfDate[1]);
//        int selectedDayOfMonth = Integer.valueOf(arrOfDate[0]);
//        if (date.equals(""))
//            return false;
//        if (selectedYear > currentYear ||
//                (selectedYear == currentYear && selectedMonth > currentMonth) ||
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth) ||
//                (selectedYear == currentYear && selectedMonth == currentMonth && selectedDayOfMonth >= currentDayOfMonth)) {
//            return true;
//        } else
//            return false;
//    }
//}
