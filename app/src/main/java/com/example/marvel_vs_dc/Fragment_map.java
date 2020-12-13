package com.example.marvel_vs_dc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_map extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap map;
    private Marker lastMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_MAP_map);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);


        initRecords();
        findViews(view);
        initViews(view);


        return view;
    }

    private void initViews(View view) {

    }

    private void findViews(View view) {

    }

    private void initRecords() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng Israel = new LatLng(31.604625, 34.939093);
        lastMarker = map.addMarker(new MarkerOptions().position(Israel).title("This is Israel"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Israel));
//        map.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Israel,7.0f));
    }

    public void showLocationOnMap(double lat, double lon, String name) {
        LatLng newCor = new LatLng(lat, lon);
        if(lastMarker != null)
            lastMarker.remove();
        lastMarker = map.addMarker(new MarkerOptions().position(newCor).title(name));
//        map.moveCamera(CameraUpdateFactory.newLatLng(newCor));
        map.animateCamera(CameraUpdateFactory.newLatLng(newCor));



    }
}