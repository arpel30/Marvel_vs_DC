package com.example.marvel_vs_dc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment_list extends Fragment {


    private TextView list_LBL_name;
    private Button list_BTN_update;

//    private CallBack_Top callBack_top;

//    public void setCallBack_top(CallBack_Top _callBack_top) {
//        this.callBack_top = _callBack_top;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView - Fragment_List");

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();


        return view;
    }

    private void initViews() {
    }

    private void findViews(View view) {
    }
}
