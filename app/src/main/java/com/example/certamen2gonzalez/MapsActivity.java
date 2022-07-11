package com.example.certamen2gonzalez;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.certamen2gonzalez.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import BD.BDGonzalez;
import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private CientificoModel cientifico;
    private ArrayList recolecciones;
    private BDGonzalez local = new BDGonzalez(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (getIntent().getExtras() != null) {
            String rut = (String) getIntent().getSerializableExtra("cientifico");
            this.recolecciones = new ArrayList();
            this.recolecciones=local.getRecoleccionByCientificoSql(rut);


        }
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
        ArrayList<RecoleccionModel> rList = this.recolecciones;
        if(rList.size()==0){
            lanzarToast("Error debe ingresar Cientifico primero");
            finish();
        }
        PolylineOptions POLILINEA = new PolylineOptions();

        if (rList.size() > 0){
            for(int i = 0; i < rList.size(); i++){
                Bitmap image = BitmapFactory.decodeByteArray(rList.get(i).getFotoLugar(), 0, rList.get(i).getFotoLugar().length);
                Bitmap resized = Bitmap.createScaledBitmap(image, 150, 150, true);
                BitmapDescriptor imageMarker = BitmapDescriptorFactory.fromBitmap(resized);

                LatLng postR = new LatLng(rList.get(i).getLatitud(), rList.get(i).getLongitud());
                String datos = "Codigo Planta: "+rList.get(i).getCodigoPlanta()+"\n Cientifico: "+rList.get(i).getRutCientifico()+"\n Comentario: "+rList.get(i).getComentario();
                mMap.addMarker(new MarkerOptions().position(postR).title(rList.get(i).getFecha()).snippet(datos).icon(imageMarker));

                POLILINEA.add(postR);
            }
            Polyline polyline = mMap.addPolyline(POLILINEA);


        }

        //Camera Position
        CameraPosition camPos = new CameraPosition.Builder()
                .target(POLILINEA.getPoints().get(0))
                .zoom(14)
                .bearing(45)
                .tilt(50)
                .build();

        CameraUpdate camMap = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camMap);

        // Add a marker in Sydney and move the camera



    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}