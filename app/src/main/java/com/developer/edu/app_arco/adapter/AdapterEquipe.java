package com.developer.edu.app_arco.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.developer.edu.app_arco.controller.ControllerEquipe;
import com.developer.edu.app_arco.model.Equipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterEquipe extends ArrayAdapter<Equipe> {

    private Context context;
    private List<Equipe> equipes;
    private String CODIGO_EQUIPE;


    public AdapterEquipe(Context context, List<Equipe> equipes, String CODIGO_EQUIPE) {
        super(context, R.layout.adapter_equipe2, equipes);
        this.context = context;
        this.equipes = equipes;
        this.CODIGO_EQUIPE = CODIGO_EQUIPE;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_equipe2, parent, false);

        final Equipe equipe = equipes.get(position);

        ImageView foto_lider = view.findViewById(R.id.id_adapter_equipe_lider);
        TextView nome = view.findViewById(R.id.id_adapter_equipe_nome);
        TextView data_nasc = view.findViewById(R.id.id_adapter_equipe_data_nasc);
        TextView escolaridade = view.findViewById(R.id.id_adapter_equipe_escolaridade);
        TextView remover_menbro = view.findViewById(R.id.id_adapter_equipe_remover_menbro);

        Picasso.get().load(URL_BASE + "/IMG/" + equipe.getID() + "_usuario.jpg").into(foto_lider);
        nome.setText(equipe.getNOME() + " " + equipe.getSOBRENOME());
        data_nasc.setText(equipe.getDATA_NASC());
        escolaridade.setText(equipe.getESCOLARIDADE());

        remover_menbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ControllerEquipe.removerMenbro(context, CODIGO_EQUIPE, equipe.getID());
            }
        });


        foto_lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerfilActivity.class);
                intent.putExtra("MEU_PERFIL", "N");
                intent.putExtra("ID_USUARIO", equipe.getID());
                context.startActivity(intent);
            }
        });

        return view;
    }

}

