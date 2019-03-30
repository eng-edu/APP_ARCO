package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.PerfilActivity;
import com.developer.edu.app_arco.model.Comentario;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterComentario extends ArrayAdapter<Comentario> {

    private Context context;
    private List<Comentario> comentarios;


    public AdapterComentario(Context context, List<Comentario> comentarios) {
        super(context, R.layout.adapter_comentario, comentarios);
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_comentario, parent, false);

        final Comentario comentario = comentarios.get(position);

        TextView data_hora = view.findViewById(R.id.id_adapter_comentario_data_hora);
        TextView texto = view.findViewById(R.id.id_adapter_comentario_texto);
        TextView id_autor = view.findViewById(R.id.id_adapter_comentario_id_autor);

        id_autor.setText("Menbro: "+comentario.getID_AUTOR());

        if(comentario.getID_AUTOR().equals(comentario.getID_LIDER())){
            id_autor.setText("Líder");
        }

        data_hora.setText(comentario.getDATA_HORA());
        texto.setText(comentario.getTEXTO());


        return view;
    }

}

