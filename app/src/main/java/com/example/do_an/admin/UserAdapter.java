//package com.example.do_an.admin;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.do_an.R;
//import com.example.do_an.database.CarDataSource;
////import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.database.UserDataSource;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.model.HisCar;
//import com.example.do_an.model.User;
//
//import java.util.List;
//
//public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
//    private List<User> userList;
//    private OnItemClickListener clickListener;
//    private Context context;
//    private RecyclerView recyclerView;
//
//
//    public UserAdapter(List<User> userList, OnItemClickListener clickListener, Context context, RecyclerView recyclerView) {
//        this.userList = userList;
//        this.clickListener = clickListener;
//        this.context = context;
//        this.recyclerView = recyclerView;
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.layout_allusers, parent, false);
//        final UserViewHolder holder = new UserViewHolder(view);
//        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    User user = userList.get(position);
//                    clickListener.onItemClick(user);
//                }
//            }
//        });
//        return holder;
//    }
//
//
//    @Override
//    public void onBindViewHolder(UserViewHolder holder, int position) {
//        User user = userList.get(position);
//        holder.username.setText("Số điện thoại: " + user.getUsername());
//        holder.password.setText("Mật khẩu       : " + String.valueOf(user.getPassword()));
//        holder.xoaacc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    User user = userList.get(position);
//                    RemoveAcc(user.getUsername());
//                    userList.remove(user);
//                    notifyDataSetChanged();
//                }
//            }
//        });
//    }
//    public void RemoveAcc(String username)
//    {
//        UserDataSource userDataSource = new UserDataSource(context);
//        userDataSource.open();
//        UserInfoDataSource userInfoDataSource = new UserInfoDataSource(context);
//        userInfoDataSource.open();
//        CarDataSource carDataSource = new CarDataSource(context);
//        carDataSource.open();
//        CarHisDataSource carHisDataSource = new CarHisDataSource(context);
//        carHisDataSource.open();
//        List<User> users = userDataSource.getAllUsers();
//        List<HisCar> hisCars = carHisDataSource.getAllHisCarAdmin();
//        for (User user : users) {
//            if (user.getUsername().equals(username))
//            {
//                userDataSource.deleteUser(user);
//                userInfoDataSource.deleteUserInfo(user.getUsername());
//                carDataSource.deleteCar(user.getUsername());
//               // carHisDataSource.
//            }
//        }
//        for (HisCar hisCar : hisCars) {
//            if (hisCar.getHisuser().equals(username))
//            {
//                carHisDataSource.deleteHisCar(hisCar);
//            }
//        }
//        userDataSource.close();
//        userInfoDataSource.close();
//        carDataSource.close();
//    }
//
//    @Override
//    public int getItemCount() {
//        return userList.size();
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(User user);
//    }
//}
//
//class UserViewHolder extends RecyclerView.ViewHolder {
//    public TextView username;
//    public TextView password;
//
//    public LinearLayout itemLayout;
//    public Button xoaacc;
//
//
//
//
//    public UserViewHolder(View itemView) {
//        super(itemView);
//        username = itemView.findViewById(R.id.tvUserPhone);
//        password = itemView.findViewById(R.id.tvUserPass);
//        itemLayout = itemView.findViewById(R.id.LLuser);
//        xoaacc = itemView.findViewById(R.id.xoaacc);
//    }
//}
//
//
