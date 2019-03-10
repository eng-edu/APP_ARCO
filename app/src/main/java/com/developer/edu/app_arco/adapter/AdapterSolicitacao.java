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
import com.developer.edu.app_arco.controller.ControllerSolicitacao;
import com.developer.edu.app_arco.model.Solicitacao;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;


public class AdapterSolicitacao extends ArrayAdapter<Solicitacao> {

    private Context context;
    private List<Solicitacao> solicitacaos;
    private String CODIGO_EQUIPE;


    public AdapterSolicitacao(Context context, List<Solicitacao> solicitacaos, String CODIGO_EQUIPE) {
        super(context, R.layout.adapter_solicitacao, solicitacaos);
        this.context = context;
        this.solicitacaos = solicitacaos;
        this.CODIGO_EQUIPE = CODIGO_EQUIPE;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_solicitacao, parent, false);

        final Solicitacao solicitacao = solicitacaos.get(position);

        ImageView foto_lider = view.findViewById(R.id.id_adapter_solicitacao_lider);
        TextView nome = view.findViewById(R.id.id_adapter_solicitacao_nome);
        TextView data_nasc = view.findViewById(R.id.id_adapter_solicitacao_data_nasc);
        TextView escolaridade = view.findViewById(R.id.id_adapter_solicitacao_escolaridade);
        TextView aceitar = view.findViewById(R.id.id_adapter_solicitacao_aceitar);
        TextView recusar = view.findViewById(R.id.id_adapter_solicitacao_recusar);


        Picasso.get().load(URL_BASE + "/IMG/" + solicitacao.getID() + "_usuario.jpg").into(foto_lider);
        nome.setText(solicitacao.getNOME() + " " + solicitacao.getSOBRENOME());
        data_nasc.setText(solicitacao.getDATA_NASC());
        escolaridade.setText(solicitacao.getESCOLARIDADE());

        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerSolicitacao.aceitarSolicitacao(context, CODIGO_EQUIPE, solicitacao.getID());
            }
        });

        recusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerSolicitacao.recusarSolicitacao(context, CODIGO_EQUIPE, solicitacao.getID());
            }
        });

        foto_lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerfilActivity.class);
                intent.putExtra("MEU_PERFIL", "N");
                intent.putExtra("ID_USUARIO", solicitacao.getID());
                context.startActivity(intent);
            }
        });

        return view;
    }

}

