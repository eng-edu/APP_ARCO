package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
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
        ImageView foto_autor = view.findViewById(R.id.id_adapter_comentario_autor);
        TextView data_hora_email = view.findViewById(R.id.id_adapter_comentario_data_hora_nome);
        TextView texto = view.findViewById(R.id.id_adapter_comentario_texto);

        Picasso.get().load(URL_BASE + "/IMG/" + comentario.getID_AUTOR() + "_usuario.jpg").into(foto_autor);
        data_hora_email.setText(comentario.getDATA_HORA() + " " + comentario.getEMAIL_AUTOR());
        texto.setText(comentario.getTEXTO());


        return view;
    }

}

