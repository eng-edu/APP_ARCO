package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.model.Arco;

import java.util.List;


public class AdapterArco extends ArrayAdapter<Arco> {

    private Context context;
    private List<Arco> arcos;

    public AdapterArco(Context context, List<Arco> arcos) {
        super(context, R.layout.adapter_arco, arcos);
        this.context = context;
        this.arcos = arcos;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_arco, parent, false);

        final Arco arco = arcos.get(position);

        TextView temaitca = view.findViewById(R.id.id_adapter_tematica);
        TextView titulo = view.findViewById(R.id.id_adapter_titulo);
        TextView pontos = view.findViewById(R.id.id_adapter_pontos);
        TextView curtidas = view.findViewById(R.id.id_adapter_curtidas);

        temaitca.setText(arco.getTEMATICA());
        titulo.setText(arco.getTITULO());
        pontos.setText(arco.getPONTO() + " pontos");
        curtidas.setText(arco.getGOSTEI() + " pessoas gostaram deste arco!");


        return view;
    }

}

