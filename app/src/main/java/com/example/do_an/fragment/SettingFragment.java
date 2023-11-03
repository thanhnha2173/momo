package com.example.do_an.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.ui.EnterSdtActivity;
import com.example.do_an.ui.LoginActivity;
import com.example.do_an.ui.LoginAndSecurityActivity;
import com.example.do_an.ui.PersonalPageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    Button btlogout;
    TextView nameUser, phoneNumber1;
    LinearLayout userPage, loginSecurity;
    private FirebaseFirestore db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        nameUser = view.findViewById(R.id.nameUser);
        phoneNumber1 = view.findViewById(R.id.phoneNumber1);
        phoneNumber1.setText(phoneNumber);
        getName(phoneNumber).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String hoTen) {
                // Use the hoTen value here or perform any action based on the result
                if(!hoTen.equals(""))
                    nameUser.setText(hoTen);
            }
        });
        btlogout = view.findViewById(R.id.out);
        userPage = view.findViewById(R.id.userPage);
        loginSecurity = view.findViewById(R.id.loginSecurity);
        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SettingsActivity.
                Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
                startActivity(intent);
            }
        });
        loginSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SettingsActivity.
                Intent intent = new Intent(getActivity(), LoginAndSecurityActivity.class);
                startActivity(intent);
            }
        });
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });


        return view;
    }
    void loadFragment(Fragment fmNew)
    {
        FragmentTransaction fmTran = getFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_fragment, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }
    public LiveData<String> getName(String phoneNumber) {
        MutableLiveData<String> hoTenLiveData = new MutableLiveData<>();

        db.collection("UsersInfo").document("CN" + phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String hoTen = document.getString("HoTen");
                                hoTenLiveData.postValue(hoTen);
                            } else {
                                hoTenLiveData.postValue(""); // Document not found or HoTen is empty
                            }
                        } else {
                            hoTenLiveData.postValue(""); // Error occurred
                        }
                    }
                });

        return hoTenLiveData;
    }



    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(requireContext(), EnterSdtActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT);
                myToast.show();
                requireActivity().finishAffinity(); // kết thúc tất cả các activity và xóa khỏi stack
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dismiss the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}