package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.model.Tematica;

import java.util.List;


public class AdapterTematica extends ArrayAdapter<Tematica> {

    private Context context;
    private List<Tematica> tematicas;

    public AdapterTematica(Context context, List<Tematica> tematicas) {
        super(context, R.layout.adapter_tematica, tematicas);
        this.context = context;
        this.tematicas = tematicas;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_tematica, parent, false);

        final Tematica tematica = tematicas.get(position);

        TextView nome = view.findViewById(R.id.id_adapter_tematica_nome);
        TextView descricao = view.findViewById(R.id.id_adapter_tematica_descricao);
        TextView idade_minima = view.findViewById(R.id.id_adapter_tematica_idade_minima);

        nome.setText(tematica.getNome());
        descricao.setText("Descrição: "+tematica.getDescricao());
        idade_minima.setText("idade mínima indicada: "+tematica.getIdade_minima() + " anos");


        return view;
    }

}

