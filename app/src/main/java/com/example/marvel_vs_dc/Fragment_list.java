package com.example.marvel_vs_dc;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_list extends Fragment {

    private View view;
    private CallBack_TopTen callBack;

    private TextView list_LBL_name;
    private Button list_BTN_update;

    private LinearLayout list_LAY_topTen;

    private TopTen topTen;
    private LinearLayout exmp_lay;
    private TextView exmp_lbl;
    private TextView exmp_place;
    private ImageView exmp_img;

//    private GoogleMap map;
//    private FusedLocationProviderClient

    public void setCallBack(CallBack_TopTen _callBack) {
        this.callBack = _callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView - Fragment_List");

        view = inflater.inflate(R.layout.fragment_list, container, false);
        initRecords();
        findViews(view);
        initViews(view);


        return view;
    }

    private void initRecords() {
        topTen = new TopTen();

        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.SP_FILE, MODE_PRIVATE);
        Gson gson = new Gson();

        String prefStr = prefs.getString(Constants.TOPTEN, "");
        Log.d("aaa", "str : " + prefStr);
        if(prefStr != "")
            topTen = gson.fromJson(prefStr, TopTen.class);
        else {
            topTen = new TopTen();
            Log.d("aaa", topTen.getRecords()+"");

        }
    }

    private void initViews(View view) {
        for (int i = 0; i < topTen.getRecords().size(); i++) {
            Record r = topTen.getRecords().get(i);
            LinearLayout lay = new LinearLayout(getContext().getApplicationContext());
            lay.setLayoutParams(exmp_lay.getLayoutParams());

            TextView lbl = new TextView(getContext().getApplicationContext());
//            TextView lbl = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
            lbl.setLayoutParams(exmp_lbl.getLayoutParams());
            lbl.setTextColor(Color.WHITE);
            lbl.setTextSize(20);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(lbl, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            lbl.setText("Player : " + r.getName() + "\nCharacter : "+ r.getHero().getName() + "\nScore : " + r.getHero().getScore() + "\nDate : " + r.getDate());
            lay.addView(lbl);

            ImageView iv = new ImageView(getContext().getApplicationContext());
            iv.setImageResource(r.getHero().getId());
            iv.setTag(r);
            iv.setLayoutParams(exmp_img.getLayoutParams());
            lay.addView(iv);

            TextView place = new TextView(getContext().getApplicationContext());
            place.setLayoutParams(exmp_place.getLayoutParams());
            place.setTextColor(Color.WHITE);
            place.setTextSize(50);
            place.setWidth(200);
            place.setGravity(Gravity.CENTER);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(place, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            place.setText((i+1)+"");
            lay.addView(place);

            list_LAY_topTen.addView(lay);
            setListener(r, lay);
        }
        exmp_lbl.setVisibility(View.GONE);
        exmp_img.setVisibility(View.GONE);
        exmp_lay.setVisibility(View.GONE);
    }

    private void setListener(Record r, LinearLayout lay) {
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.displayLocation(r.getLat(), r.getLon());
                }

            }
        });
    }

    private void findViews(View view) {
        list_LAY_topTen = view.findViewById(R.id.list_LAY_topTen);
        exmp_img = view.findViewById(R.id.list_IMG_exmpImg);
        exmp_lay = view.findViewById(R.id.list_LAY_exmpLay);
        exmp_lbl = view.findViewById(R.id.list_LBL_exmpLbl);
        exmp_place = view.findViewById(R.id.list_LBL_place);
    }
}
