package com.example.certamen2gonzalez;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.certamen2gonzalez.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng losAngeles = new LatLng(-37.4629, -72.3612);
        Marker los_Angeles=mMap.addMarker(new MarkerOptions()
                .position(losAngeles)
                .title("Los Angeles")
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.img)));

        LatLng SantaBarbara = new LatLng(-37.6644, -72.0246);
        mMap.addMarker(new MarkerOptions().position(SantaBarbara).title("Marker in Santa Barbara"));

        LatLng Angol = new LatLng(-37.8061, -72.7038);
        mMap.addMarker(new MarkerOptions().position(Angol).title("Marker in Angol"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(losAngeles));
        PolylineOptions POLILINEA =new PolylineOptions()
                .add(losAngeles)
                .add(SantaBarbara)
                .add(Angol)
                .add(losAngeles);

        Polyline polyline=mMap.addPolyline(POLILINEA);
        PolylineOptions POLILINEA2 =new PolylineOptions()
                .add(new LatLng(-37.5923, -72.4898))
                .add(new LatLng(-37.7531, -72.4076))
                .add(new LatLng(-37.5523, -72.2115))
                .add(new LatLng(-37.5923, -72.4898));
        Polyline polyline2=mMap.addPolyline(POLILINEA2);
        	PolygonOptions POLIGONO =new PolygonOptions()
        	.add(losAngeles,SantaBarbara,Angol)
        	.strokeColor(Color.YELLOW)
        	.fillColor(Color.YELLOW);
        Polygon poligono2=mMap.addPolygon(POLIGONO);
        PolygonOptions POLIGONO2 =new PolygonOptions()
                .add(new LatLng(-37.5923, -72.4898),new LatLng(-37.7531, -72.4076),new LatLng(-37.5523, -72.2115))
                .strokeColor(Color.BLACK)
                .fillColor(Color.BLACK);
        Polygon poligono=mMap.addPolygon(POLIGONO2);



    }
}