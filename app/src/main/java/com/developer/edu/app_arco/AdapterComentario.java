package com.developer.edu.app_arco;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.model.Comentario;
import com.developer.edu.app_arco.model.Usuario;
import com.squareup.picasso.MemoryPolicy;
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


        TextView email = view.findViewById(R.id.id_adapter_comentario_email);
        TextView data_hora = view.findViewById(R.id.id_adapter_comentario_data_hora);
        TextView comentario_ = view.findViewById(R.id.id_adapter_comentario_comentario);
        ImageView fotoPerfil = view.findViewById(R.id.id_adapter_comentario_image_perfil);

        email.setText(comentario.getEmail().toString());
        data_hora.setText(comentario.getData().toString() +" " + comentario.getHora().toString());
        comentario_.setText(comentario.getTetxo().toString());


        Picasso.get().load(URL_BASE + "/IMG/" + comentario.getId() + "_usuario.jpg").into(fotoPerfil);

        return view;
    }

}

