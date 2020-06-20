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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hina.washmycarpro.OptionActivity;
import com.hina.washmycarpro.R;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
       // String[] locationName = {"Fahad Car Wash ","PSO Car Wash","Auto Foam Car Wash & Detailing","Ali Nawaz Car Wash","Luxury Car Detailing"};


        final HashMap<String, String> markerMap = new HashMap<String, String>();
        Marker markerOne = mGoogleMap.addMarker(new MarkerOptions().position(FahadCarWash)
                .title("Fahad Car Wash")
                .snippet("Karachi City, Sindh, Pakistan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)));
        String idOne = markerOne.getId();
        markerMap.put(idOne, "action_one");
        markerOne.showInfoWindow();

        Marker markerTwo = mGoogleMap.addMarker(new MarkerOptions()
                .position(LuxuryCarDetail)
                .title("Luuxury Car Detailing")
                .snippet("Karachi City, Sindh, Pakistan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)));
        String idTwo = markerTwo.getId();
        markerMap.put(idTwo, "action_two");
        markerTwo.showInfoWindow();

        Marker markerThree = mGoogleMap.addMarker(new MarkerOptions()
                .position(PSOCarWash)
                .title("PSO Car Wash")
                .snippet("Karachi City, Sindh, Pakistan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)));
        String idThree = markerThree.getId();
        markerMap.put(idThree, "action_three");
        markerThree.showInfoWindow();

        Marker markerFour = mGoogleMap.addMarker(new MarkerOptions()
                .position(AutoFoamCarWash)
                .title("Auto Foam Car Wash and Detailing")
                .snippet("Karachi City, Sindh, Pakistan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)));
        String idFour = markerFour.getId();
        markerMap.put(idFour, "action_four");
        markerFour.showInfoWindow();

        Marker markerFive = mGoogleMap.addMarker(new MarkerOptions()
                .position(AliNawazCarWash)
                .title("Ali Nawaz Car Wash")
                .snippet("Karachi City, Sindh, Pakistan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer_icon)));
        String idFive = markerFive.getId();
        markerMap.put(idFive, "action_five");
        markerFive.showInfoWindow();
        markerTwo.showInfoWindow();

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12f));
        CameraPosition PSOwash = CameraPosition.builder().target(new LatLng(24.853240, 66.993997)).zoom(12f).bearing(0).tilt(45).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(24.916369, 66.956950)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(PSOwash));
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(), OptionActivity.class);
                String actionId = markerMap.get(marker.getId());

                if (actionId.equals("action_one")) {
                    intent.putExtra("key","Fahad Car Wash");
                    startActivity(intent);
                } else if (actionId.equals("action_two")) {
                    intent.putExtra("key","Luxury Car Detailing");
                    startActivity(intent);
                } else if(actionId.equals("action_three")){
                    intent.putExtra("key","PSO Car Wash");
                    startActivity(intent);
                } else if(actionId.equals("action_four")){
                    intent.putExtra("key","Auto Foam Car Wash and Detailing");
                    startActivity(intent);
                }else if(actionId.equals("action_five")){
                    intent.putExtra("key","Ali Nawaz Car Wash");
                    startActivity(intent);
                }
            }
        });
        /*for (int i=0; i<arrayList.size(); i++){

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

        }*/
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);


    }
}