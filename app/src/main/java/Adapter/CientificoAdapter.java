package Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.certamen2gonzalez.R;
import androidx.annotation.NonNull;

import java.util.List;

import Models.CientificoModel;

public class CientificoAdapter extends ArrayAdapter<CientificoModel> {
    View CientificoList;

    public CientificoAdapter(@NonNull Context context, int resource, List<CientificoModel> objects)
    { super(context, resource, objects); }

    @Override
    public View getView(int position, View covertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CientificoList = covertView;
        CientificoList = inflater.inflate(R.layout.cientifico_item, parent, false);

        TextView rut = (TextView) CientificoList.findViewById(R.id.tvRut);
        TextView nombre = (TextView) CientificoList.findViewById(R.id.tvNombre);
        TextView materno = (TextView) CientificoList.findViewById(R.id.tvApellidoMaterno);
        TextView paterno = (TextView) CientificoList.findViewById(R.id.tvApellidoPaterno);
        TextView tvSexo  = (TextView) CientificoList.findViewById(R.id.tvSexo);



        CientificoModel cientifico = getItem(position);
        rut.setText(cientifico.getRut() + "");
        nombre.setText(cientifico.getNombre()+"");
        materno.setText(cientifico.getApellidoMaterno()+"");
        paterno.setText(cientifico.getApellidoPaterno()+"");
        if(cientifico.getSexo()=="Masculino")
            tvSexo.setText("M");
        else
            tvSexo.setText("F");

        return CientificoList;
    }
}