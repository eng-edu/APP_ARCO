package com.developer.edu.app_arco.adapter;

import android.annotation.SuppressLint;
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
import com.developer.edu.app_arco.model.Ranking;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterRanking extends ArrayAdapter<Ranking> {

    private Context context;
    private List<Ranking> rankings;

    public AdapterRanking(Context context, List<Ranking> rankings) {
        super(context, R.layout.adapter_ranking, rankings);
        this.context = context;
        this.rankings = rankings;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_ranking, parent, false);

        final Ranking ranking = rankings.get(position);


        final ImageView foto_perfil = view.findViewById(R.id.id_adapter_raking_feed_foto_user);
        Picasso.get().load(URL_BASE + "/IMG/" + ranking.getID_USUARIO() + "_usuario.jpg").into(foto_perfil);
        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerfilActivity.class);
                intent.putExtra("MEU_PERFIL", "N");
                intent.putExtra("ID_USUARIO", ranking.getID_USUARIO());
                context.startActivity(intent);
            }
        });


        TextView curtidas = view.findViewById(R.id.id_adapter_ranking_curtidas);
        TextView estrelas = view.findViewById(R.id.id_adapter__ranking_estrelas);
        ImageView trofeu = view.findViewById(R.id.id_adapter_ranking_trofeu);

        curtidas.setText("curtidas: " + ranking.getCURTIDAS());
        estrelas.setText("estrelas: " + ranking.getESTRELAS());

        if (position == 0) {
            trofeu.setImageResource(R.mipmap.ic_trofeuranking);
        }


        return view;
    }

}

