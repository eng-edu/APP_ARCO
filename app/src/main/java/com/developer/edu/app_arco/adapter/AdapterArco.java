package com.developer.edu.app_arco.adapter;

import android.content.Context;
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
import com.developer.edu.app_arco.model.Arco;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterArco extends ArrayAdapter<Arco> {

    private Context context;
    private List<Arco> arcos;

    public AdapterArco(Context context, List<Arco> arcos) {
        super(context, R.layout.adapter_meus_arcos, arcos);
        this.context = context;
        this.arcos = arcos;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_meus_arcos, parent, false);

        final Arco arco = arcos.get(position);

        String str_status = "";
        if(arco.getSITUACAO().equals("1")){
            str_status = "Em Desenvolvimento";
        }else if(arco.getSITUACAO().equals("2")){
            str_status = "Finalizado";
        }

        TextView status = view.findViewById(R.id.id_adapter_arco_feed_status);
        TextView data_hora = view.findViewById(R.id.id_adapter_arco_feed_data_hora);
        TextView nome_tematica = view.findViewById(R.id.id_adapter_arco_feed_nome_tematica);
        ImageView foto_lider = view.findViewById(R.id.id_adapter_arco_feed_foto_lider);
        ImageView foto_menbro = view.findViewById(R.id.id_adapter_arco_feed_foto_menbro);

        status.setText("Status: " + str_status);
        data_hora.setText("Criando em: "+ arco.getDATA_HORA());
        nome_tematica.setText("Temática: " + arco.getNOME_TEMATICA());
        Picasso.get().load(URL_BASE + "/IMG/" + arco.getID_LIDER() + "_usuario.jpg").into(foto_lider);


        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String TIPO = sharedPreferences.getString("TIPO", "");


        if(TIPO.equals("2")){
            foto_menbro.setVisibility(View.VISIBLE);
            Picasso.get().load(URL_BASE + "/IMG/" + ID_USUARIO + "_usuario.jpg").into(foto_menbro);
        }else {
            foto_menbro.setVisibility(View.GONE);
        }


        return view;
    }

}

