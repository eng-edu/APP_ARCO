package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterArco;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerEtapa;
import com.developer.edu.app_arco.model.Arco;
import com.developer.edu.app_arco.model.Etapa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EtapaActivity extends AppCompatActivity {

    Socket socket = SocketStatic.getSocket();
    ArrayAdapter<Etapa> arrayAdapter;
    ListView lw_etapas;


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

        ControllerEtapa.buscarEtapa(getWindow().getDecorView(), ID_ETAPA, TIPO, swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ControllerEtapa.buscarEtapa(getWindow().getDecorView(), ID_ETAPA, TIPO, swipeRefreshLayout);
            }
        });

        LinearLayout layout_escrever = findViewById(R.id.id_etapa_layout_escrever);
        LinearLayout layout_finalizar = findViewById(R.id.id_etapa_layout_finalizar);

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

        final ListView listView = findViewById(R.id.id_etapa_lista_opinioes);
        final AdapterArco arrayAdapter = new AdapterArco(EtapaActivity.this, new ArrayList<Arco>());


        socket.on("OPINIAO".concat(ID_ARCO), new Emitter.Listener() {
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
                            List<Arco> arcos = new ArrayList<>();

                            for (int i = 0; i < size; i++) {

                                JSONObject object = array.getJSONObject(i);

                                arcos.add(new Arco(
                                        object.getString("ID"),
                                        object.getString("SITUACAO"),
                                        object.getString("ID_LIDER"),
                                        object.getString("NOME_TEMATICA"),
                                        object.getString("DESCRICAO_TEMATICA"),
                                        object.getString("DATA_HORA")));

                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(arcos);
                            listView.setAdapter(arrayAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final String MEUS_ARCOS = getIntent().getStringExtra("MEUS_ARCOS");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");
        startActivity(new Intent(EtapaActivity.this, ArcoActivity.class).putExtra("ID_ARCO", ID_ARCO).putExtra("MEUS_ARCOS", MEUS_ARCOS));
        finish();
    }
}
