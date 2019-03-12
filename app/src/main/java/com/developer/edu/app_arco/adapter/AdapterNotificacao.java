package com.developer.edu.app_arco.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.ArcoActivity;
import com.developer.edu.app_arco.model.Notificacao;

import java.util.List;


public class AdapterNotificacao extends ArrayAdapter<Notificacao> {

    private Context context;
    private List<Notificacao> notificacao;

    public AdapterNotificacao(Context context, List<Notificacao> notificacaos) {
        super(context, R.layout.adapter_notificacao, notificacaos);
        this.context = context;
        this.notificacao = notificacaos;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_notificacao, parent, false);

        final Notificacao notify = notificacao.get(position);

        TextView data_hora = view.findViewById(R.id.id_adapter_notificacao_data_hora);
        TextView texto = view.findViewById(R.id.id_adapter_notificacao_texto);

        data_hora.setText(notify.getDATA_HORA());
        texto.setText(notify.getTEXTO());


        if(notify.getSITUACAO().equals("3")){
            data_hora.setTextColor(Color.GRAY);
            texto.setTextColor(Color.GRAY);
        }

        return view;
    }

}

