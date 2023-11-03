//package com.example.do_an.fragment;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.example.do_an.database.CarDataSource;
//import com.example.do_an.database.CarHisDataSource;
//import com.example.do_an.R;
//import com.example.do_an.database.UserDataSource;
//import com.example.do_an.database.UserInfoDataSource;
//import com.example.do_an.model.HisCar;
//import com.example.do_an.model.User;
//import com.example.do_an.ui.LoginActivity;
//
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * create an instance of this fragment.
// */
//public class SettingAccFragment extends Fragment {
//    ImageButton imbbackFraSeAcc;
//    Button btchangePass, btRemoveAcc;
//    private UserDataSource mDataSource;
//    private UserInfoDataSource mInfoDataSource;
//    private CarDataSource mCarDataSource;
//    private CarHisDataSource mHisCarDataSource;
//
//    public SettingAccFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_setting_acc, container, false);
//        imbbackFraSeAcc = view.findViewById(R.id.imbbackFraSeAcc);
//        btchangePass = view.findViewById(R.id.changePass);
//        btRemoveAcc = view.findViewById(R.id.removeAcc);
//        mDataSource = new UserDataSource(getContext());
//        mCarDataSource = new CarDataSource(getContext());
//        mInfoDataSource = new UserInfoDataSource(getContext());
//        mHisCarDataSource = new CarHisDataSource(getContext());
//        mDataSource.open();
//        mInfoDataSource.open();
//        mHisCarDataSource.open();
//        mCarDataSource.open();
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        imbbackFraSeAcc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().popBackStack();
//            }
//        });
//
//        btchangePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadFragment(new ChangPassFragment());
//            }
//        });
//
//        btRemoveAcc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showDialog(username);
//
//            }
//        });
//
//        return view;
//    }
//
//    private void showDialog(String username) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Thông báo");
//        builder.setMessage("Bạn có chắc chắn muốn xoá tài khoản?");
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                List<User> users = mDataSource.getAllUsers();
//                List<HisCar> hisCars = mHisCarDataSource.getAllHisCarAdmin();
//                for (User user : users) {
//                    if (user.getUsername().equals(username))
//                    {
//                        mDataSource.deleteUser(user);
//                        mInfoDataSource.deleteUserInfo(user.getUsername());
//                        mCarDataSource.deleteCar(user.getUsername());
//                    }
//                }
//                for (HisCar hisCar : hisCars) {
//                    if (hisCar.getHisuser().equals(username))
//                    {
//                        mHisCarDataSource.deleteHisCar(hisCar);
//                    }
//                }
//                mDataSource.close();
//                mInfoDataSource.close();
//                mCarDataSource.close();
//                mHisCarDataSource.close();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                Toast myToast = Toast.makeText(getContext(), "Xoá tài khoản thành công", Toast.LENGTH_SHORT);
//                myToast.show();
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    void loadFragment(Fragment fmNew) {
//        FragmentTransaction fmTran = getFragmentManager().beginTransaction();
//        fmTran.replace(R.id.main_fragment, fmNew);
//        fmTran.addToBackStack(null);
//        fmTran.commit();
//    }
//}