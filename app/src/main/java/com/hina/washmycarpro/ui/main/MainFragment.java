package com.hina.washmycarpro.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hina.washmycarpro.OptionActivity;
import com.hina.washmycarpro.R;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFragment extends Fragment implements OnMapReadyCallback {

    private MainViewModel mainViewModel;

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng FahadCarWash = new LatLng(24.916369, 66.956950);
    LatLng LuxuryCarDetail = new LatLng(24.868873, 66.985375);
    LatLng AutoFoamCarWash = new LatLng(24.942523, 67.035608);
    LatLng AliNawazCarWash = new LatLng(24.874982, 67.045774);
    LatLng PSOCarWash = new LatLng(24.853240, 66.993997);







    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       //mainViewModel =
               // ViewModelProviders.of(this).get(MainViewModel.class);
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        /*final TextView textView = root.findViewById(R.id.text_map);

        mainViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        arrayList.add(FahadCarWash);
        arrayList.add(PSOCarWash);
        arrayList.add(AutoFoamCarWash);
        arrayList.add(AliNawazCarWash);
        arrayList.add(LuxuryCarDetail);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        String[] locationName = {"Fahad Car Wash ","PSO Car Wash","Auto Foam Car Wash & Detailing","Ali Nawaz Car Wash","Luxury Car Detailing"};

        for (int i=0; i<arrayList.size(); i++){

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(arrayList.get(i))
                    .title(locationName[i])
                    .snippet("Karachi City, Sindh, Pakistan")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)))
                    .showInfoWindow();
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12.5f));
            CameraPosition PSOwash = CameraPosition.builder().target(new LatLng(24.853240, 66.993997)).zoom(12).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(PSOwash));
            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    startActivity(new Intent(getActivity(), OptionActivity.class));
                }
            });

        }
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);


    }
}