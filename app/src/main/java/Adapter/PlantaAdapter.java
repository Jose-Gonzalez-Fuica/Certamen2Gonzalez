package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.certamen2gonzalez.R;

import java.util.List;


import Models.PlantaModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class PlantaAdapter extends ArrayAdapter<PlantaModel> {
    View PlantaList;

    public PlantaAdapter(@NonNull Context context, int resource, List<PlantaModel> objects)
    { super(context, resource, objects); }

    @Override
    public View getView(int position, View covertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PlantaList = covertView;
        PlantaList = inflater.inflate(R.layout.planta_item, parent, false);

        TextView codigo = (TextView) PlantaList.findViewById(R.id.tvCodigoPlanta);
        TextView nombre = (TextView) PlantaList.findViewById(R.id.tvNombrePlanta);
        TextView uso = (TextView) PlantaList.findViewById(R.id.tvUsoPlanta);
        TextView nombreCientifico = (TextView) PlantaList.findViewById(R.id.tvNombreCientificoPlanta);
        ImageView ivPlantaAdapter = (ImageView) PlantaList.findViewById(R.id.ivPlantaAdapter);


        PlantaModel planta = getItem(position);
        codigo.setText(planta.getCodigoPlanta() + "");
        nombre.setText(planta.getNombrePlanta()+"");
        uso.setText(planta.getUso()+"");
        nombreCientifico.setText(planta.getNombreCientifico()+"");
        Bitmap image = BitmapFactory.decodeByteArray(planta.getFotoPlanta(), 0, planta.getFotoPlanta().length);
        ivPlantaAdapter.setImageBitmap(image);

        return PlantaList;
    }
}
