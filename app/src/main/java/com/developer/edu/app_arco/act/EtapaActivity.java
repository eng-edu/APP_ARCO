package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterOpiniao;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerEtapa;
import com.developer.edu.app_arco.model.Etapa;
import com.developer.edu.app_arco.model.Opiniao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class EtapaActivity extends AppCompatActivity {

    Socket socket = SocketStatic.getSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_etapa);


        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String TIPO = sharedPreferences.getString("TIPO", "");
        final String MEUS_ARCOS = getIntent().getStringExtra("MEUS_ARCOS");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");
        final String ID_ETAPA = getIntent().getStringExtra("ID_ETAPA");
        final String SITUACAO_ETAPA = getIntent().getStringExtra("SITUACAO_ETAPA");
        final String CODIGO_ETAPA = getIntent().getStringExtra("CODIGO_ETAPA");
        ControllerEtapa.buscarEtapa(getWindow().getDecorView(), ID_ETAPA, TIPO, swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ControllerEtapa.buscarEtapa(getWindow().getDecorView(), ID_ETAPA, TIPO, swipeRefreshLayout);
                socket.emit("OPINIAO", ID_ETAPA);
            }
        });

        LinearLayout layout_escrever = findViewById(R.id.id_etapa_layout_escrever);
        LinearLayout layout_finalizar = findViewById(R.id.id_etapa_layout_finalizar);


        layout_escrever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerEtapa.buscarOpiniao(EtapaActivity.this, inflater, ID_USUARIO, ID_ETAPA);
            }
        });

        layout_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerEtapa.finalizarEtapa(EtapaActivity.this,ID_ETAPA, CODIGO_ETAPA, ID_ARCO, MEUS_ARCOS, ID_USUARIO);
            }
        });

        if (MEUS_ARCOS.equals("S")) {
            if (TIPO.equals("1")) {
                layout_finalizar.setVisibility(View.VISIBLE);
                layout_escrever.setVisibility(View.GONE);
            } else {
                layout_finalizar.setVisibility(View.GONE);
                layout_escrever.setVisibility(View.VISIBLE);
            }
        } else {
            layout_finalizar.setVisibility(View.GONE);
            layout_escrever.setVisibility(View.GONE);
        }

        if (SITUACAO_ETAPA.equals("2")) {
            layout_finalizar.setVisibility(View.GONE);
            layout_escrever.setVisibility(View.GONE);
        }

        final ListView listView = findViewById(R.id.id_etapa_lista_opinioes);
        final AdapterOpiniao arrayAdapter = new AdapterOpiniao(EtapaActivity.this, new ArrayList<Opiniao>(),MEUS_ARCOS);

        socket.emit("OPINIAO", ID_ETAPA);
        socket.on("OPINIAO".concat(ID_ETAPA), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                EtapaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        swipeRefreshLayout.setRefreshing(false);


                        try {

                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            List<Opiniao> opinioes = new ArrayList<>();

                            for (int i = 0; i < size; i++) {

                                JSONObject object = array.getJSONObject(i);
                                opinioes.add(new Opiniao(
                                        object.getString("ID"),
                                        object.getString("ID_ETAPA"),
                                        object.getString("NOME_ETAPA"),
                                        object.getString("ID_USUARIO"),
                                        object.getString("DATA_HORA"),
                                        object.getString("TEXTO"),
                                        object.getString("ID_LIDER")));

                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(opinioes);
                            listView.setAdapter(arrayAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (TIPO.equals("1")) {
                    startActivity(new Intent(EtapaActivity.this, PerfilActivity.class).putExtra("ID_USUARIO", arrayAdapter.getItem(position).getID_USUARIO()).putExtra("MEU_PERFIL", "N"));
                } else if (TIPO.equals("2")) {
                    startActivity(new Intent(EtapaActivity.this, PerfilActivity.class).putExtra("ID_USUARIO", arrayAdapter.getItem(position).getID_LIDER()).putExtra("MEU_PERFIL", "N"));
                }

                return false;
            }
        });


    }
}
