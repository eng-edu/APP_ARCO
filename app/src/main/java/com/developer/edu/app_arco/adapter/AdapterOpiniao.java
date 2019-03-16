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
import com.developer.edu.app_arco.model.Opiniao;

import java.util.List;

public class AdapterOpiniao extends ArrayAdapter<Opiniao> {

    private Context context;
    private List<Opiniao> opinioes;

    public AdapterOpiniao(Context context, List<Opiniao> opinioes) {
        super(context, R.layout.adapter_opiniao, opinioes);
        this.context = context;
        this.opinioes = opinioes;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_opiniao, parent, false);

        TextView data_hora = view.findViewById(R.id.id_adatper_opiniao_data_hora);
        TextView nome_etapa = view.findViewById(R.id.id_adatper_opiniao_nome_etapa);
        TextView texto = view.findViewById(R.id.id_adapter_opiniao_texto);


        final Opiniao opiniao = opinioes.get(position);

        data_hora.setText(opiniao.getDATA_HORA());
        nome_etapa.setText(opiniao.getNOME_ETAPA());
        texto.setText(opiniao.getTEXTO());




        return view;
    }

}

