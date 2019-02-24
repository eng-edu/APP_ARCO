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
import com.developer.edu.app_arco.model.Usuario;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterUsuario extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarios;

    public AdapterUsuario(Context context, List<Usuario> usuarios) {
        super(context, R.layout.adapter_usuario, usuarios);
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_usuario, parent, false);

        final Usuario usuario = usuarios.get(position);

        TextView nome = view.findViewById(R.id.id_adapter_arco_tematica);
        TextView email = view.findViewById(R.id.id_adapter_arco_email);
        ImageView fotoPerfil = view.findViewById(R.id.id_adapter_arco_image_perfil);

        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());

        Picasso.get().load(URL_BASE + "/IMG/" + usuario.getId() + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(fotoPerfil);

        return view;
    }

}

