//package com.example.do_an.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.do_an.database.CarDataSource;
//import com.example.do_an.MainActivity;
//import com.example.do_an.R;
//import com.example.do_an.model.CarInfo;
//import com.example.do_an.ui.EditCarActivity;
//
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * create an instance of this fragment.
// */
//public class MyCarFragment extends Fragment {
//
//    TextView tvnameCar, tvbienso, tvdtxl;
//    Button btdatDV;
//    LinearLayout LLCar;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_my_car, container, false);
//
//        tvnameCar = view.findViewById(R.id.tvnameCar);
//        tvbienso = view.findViewById(R.id.tvbienso);
//        tvdtxl = view.findViewById(R.id.tvdtxl);
//        btdatDV = view.findViewById(R.id.btdatDV);
//        LLCar = view.findViewById(R.id.LLCar);
//        setTV();
//        btdatDV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("fragment2", "local");
//                startActivity(intent);
//            }
//        });
//        LLCar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), EditCarActivity.class);
//                startActivity(i);
//            }
//        });
//        return view;
//    }
//
//    private void setTV() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("usernamE", "");
//        CarDataSource mCarDataSource = new CarDataSource(getContext());
//        mCarDataSource.open();
//        List<CarInfo> carInfos = mCarDataSource.getAllCar();
//        for (CarInfo carInfo : carInfos) {
//            if (carInfo.getCaruser().equals(username)) {
//                if (carInfo.getNamecar() != null)
//                    tvnameCar.setText(carInfo.getNamecar().toUpperCase());
//                if (carInfo.getBienso() != null)
//                    tvbienso.setText("Biển số xe: "+ carInfo.getBienso().toUpperCase());
//                if (carInfo.getDtxl() != null)
//                    tvdtxl.setText("Dung tích xi xanh: "+carInfo.getDtxl().toUpperCase());
//            }
//
//        }
//        mCarDataSource.close();
//    }
//}