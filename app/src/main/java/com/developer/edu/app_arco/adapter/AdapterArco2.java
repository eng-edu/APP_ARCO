package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.model.Arco;

import java.util.List;


public class AdapterArco2 extends ArrayAdapter<Arco> {

    private Context context;
    private List<Arco> arcos;

    public AdapterArco2(Context context, List<Arco> arcos) {
        super(context, R.layout.adapter_arco2, arcos);
        this.context = context;
        this.arcos = arcos;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_arco2, parent, false);

        final Arco arco = arcos.get(position);

        TextView temaitca = view.findViewById(R.id.id_adapter_tematica2);
        TextView titulo = view.findViewById(R.id.id_adapter_titulo2);
        TextView pontos = view.findViewById(R.id.id_adapter_pontos2);
        TextView curtidas = view.findViewById(R.id.id_adapter_curtidas2);
        CheckBox checkBox = view.findViewById(R.id.id_adapter_checkBox);

        temaitca.setText(arco.getTEMATICA());
        titulo.setText(arco.getTITULO());
        pontos.setText(arco.getPONTO() + " pontos");
        curtidas.setText(arco.getGOSTEI() + " pessoas gostaram deste arco!");
        checkBox.setChecked(arco.getCheck());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    arco.setCheck(true);
                } else {
                    arco.setCheck(false);
                }
            }
        });

        return view;
    }

}

