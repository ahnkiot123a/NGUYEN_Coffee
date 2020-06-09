package com.koit.project_prm391_1;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private String[] addMapType = {"CHẾ ĐỘ XEM: BÌNH THƯỜNG", "CHẾ ĐỘ XEM: VỆ TINH"};
    private Spinner spinner;
    private LatLng lngDefaultShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = findViewById(R.id.toolbarGeneral);

        View tbView = findViewById(R.id.toolbarGeneral);
        TextView tvToolbarTitle = tbView.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("Địa chỉ nhà hàng");
        toolbar.setTitle("");
        lngDefaultShop = new LatLng(21.013514, 105.525241);

        changeTyeOfMap();
    }

    private void changeTyeOfMap() {
        spinner = findViewById(R.id.changeTypeMap);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, addMapType);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        addEvents();
    }

    private void addEvents() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void backToRestaurant(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lngDefaultShop,18));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mMap.setMyLocationEnabled(true);
//            new GoogleMap.OnMyLocationChangeListener() {
//                @Override
//                public void onMyLocationChange(Location location) {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
//                }
//            };
        }
        //When Map Loads Successfully
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                LatLng lngShop1 = new LatLng(21.013906, 105.525415);
                LatLng lngShop2 = new LatLng(21.012963, 105.524824);
                mMap.addMarker(new MarkerOptions().position(lngShop1).title("NGUYEN COFFEE").snippet("Tòa nhà Beta, Đại học FPT").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marker_48)));
                mMap.addMarker(new MarkerOptions().position(lngShop2).title("NGUYEN COFFEE").snippet("Nhà ăn đại học FPT").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marker_48)));

                //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(lngShop1); //Taking Point A (First LatLng)
                builder.include(lngShop2); //Taking Point B (Second LatLng)
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }


}
