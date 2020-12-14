package com.example.marvel_vs_dc.Fragments;

import android.graphics.Color;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.marvel_vs_dc.Others.CallBack_TopTen;
import com.example.marvel_vs_dc.Others.Constants;
import com.example.marvel_vs_dc.Objects.MySPV;
import com.example.marvel_vs_dc.Objects.Record;
import com.example.marvel_vs_dc.Objects.TopTen;
import com.example.marvel_vs_dc.R;
import com.google.gson.Gson;

public class Fragment_list extends Fragment {

    private ImageView bg;

    private View view;
    private CallBack_TopTen callBack;

    private TextView list_LBL_name;
    private Button list_BTN_update;

    private LinearLayout list_LAY_topTen;

    private TopTen topTen;

    // example views for using their design
    private LinearLayout exmp_lay;
    private TextView exmp_lbl;
    private TextView exmp_place;
    private ImageView exmp_img;

    public void setCallBack(CallBack_TopTen _callBack) {
        this.callBack = _callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list, container, false);
        initRecords();
        findViews(view);
        initViews(view);
        return view;
    }

    private void initRecords() {
        // read topten from memory
        topTen = new TopTen();

        Gson gson = new Gson();

        String prefStr = MySPV.getInstance().getString(Constants.TOPTEN, "");
        if(prefStr != "")
            topTen = gson.fromJson(prefStr, TopTen.class);
        else {
            topTen = new TopTen();
        }
    }

    private void initViews(View view) {
        // init views
        setImage(getActivity().getResources().getIdentifier(Constants.Thunder_BG, null, getActivity().getPackageName()), bg);

        // init topten list dynamically
        for (int i = 0; i < topTen.getRecords().size(); i++) {
            Record r = topTen.getRecords().get(i);
            LinearLayout lay = new LinearLayout(getContext().getApplicationContext());
            lay.setLayoutParams(exmp_lay.getLayoutParams());

            TextView lbl = new TextView(getContext().getApplicationContext());
            lbl.setLayoutParams(exmp_lbl.getLayoutParams());
            lbl.setTextColor(Color.WHITE);
            lbl.setTextSize(20);
            TextViewCompat.setAutoSizeTextTypeWithDefaults(lbl, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            lbl.setText("Player : " + r.getName() + "\nCharacter : "+ r.getHero().getName() + "\nScore : " + r.getHero().getScore() + "\nDate : " + r.getDate());
            lay.addView(lbl);

            ImageView iv = new ImageView(getContext().getApplicationContext());
            setImage(r.getHero().getId(), iv);
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


            lay.setBackgroundColor(Color.parseColor("#2F33CCCC"));

            list_LAY_topTen.addView(lay);

            setListener(r, lay);
        }
        // remove examples
        exmp_lbl.setVisibility(View.GONE);
        exmp_img.setVisibility(View.GONE);
        exmp_lay.setVisibility(View.GONE);
    }

    private void setListener(Record r, LinearLayout lay) {
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.displayLocation(r.getLat(), r.getLon(), r.getName());
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

        bg = view.findViewById(R.id.list_IMG_bg);
    }

    public void setImage ( int id, ImageView view){
        Glide
                .with(getActivity())
                .load(id)
                .into(view);
    }
}
