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


import Models.RecoleccionModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class RecoleccionAdapter extends ArrayAdapter<RecoleccionModel> {
    View RecolecccionList;

    public RecoleccionAdapter(@NonNull Context context, int resource, List<RecoleccionModel> objects)
    { super(context, resource, objects); }

    @Override
    public View getView(int position, View covertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RecolecccionList = covertView;
        RecolecccionList = inflater.inflate(R.layout.recoleccion_item, parent, false);
        TextView fecha = (TextView) RecolecccionList.findViewById(R.id.tvFechaRecoleccion);
        TextView codigo = (TextView) RecolecccionList.findViewById(R.id.tvCodigoPlantaRecoleccion);
        TextView rut = (TextView) RecolecccionList.findViewById(R.id.tvRutCientificoRecoleccion);
        TextView comentario = (TextView) RecolecccionList.findViewById(R.id.tvComentarioRecoleccion);
        ImageView ivPlantaAdapter = (ImageView) RecolecccionList.findViewById(R.id.ivRecoleccionAdapter);
        TextView latitud = (TextView) RecolecccionList.findViewById(R.id.tvLatitud);
        TextView longitud = (TextView) RecolecccionList.findViewById(R.id.tvLongitud);

        RecoleccionModel recoleccion = getItem(position);
        fecha.setText(recoleccion.getFecha()+"");
        codigo.setText(recoleccion.getCodigoPlanta() + "");
        rut.setText(recoleccion.getRutCientifico()+"");
        comentario.setText(recoleccion.getComentario()+"");
        Bitmap image = BitmapFactory.decodeByteArray(recoleccion.getFotoLugar(), 0, recoleccion.getFotoLugar().length);
        ivPlantaAdapter.setImageBitmap(image);

        return RecolecccionList;
    }
}