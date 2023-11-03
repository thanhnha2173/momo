//package com.example.do_an.fragment;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//
//import com.example.do_an.R;
//
///**
// * A simple {@link Fragment} subclass.
// * create an instance of this fragment.
// */
//public class ChangeLangFragment extends Fragment {
//    ImageButton imbbackFraChLa;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.fragment_change_lang, container, false);
//        imbbackFraChLa = view.findViewById(R.id.imbbackFraChLa);
//        imbbackFraChLa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().popBackStack();
//            }
//        });
//        return  view;
//    }
//}