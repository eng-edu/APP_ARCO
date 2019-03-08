package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class ArcoActivity extends AppCompatActivity {

    CardView etapa1;
    CardView etapa2;
    CardView etapa3;
    CardView etapa4;
    CardView etapa5;

    TextView status;
    TextView data_hora;
    TextView nome_equipe;
    TextView nome_tematica;
    ImageView foto_lider;

    ImageView status_e1;
    ImageView status_e2;
    ImageView status_e3;
    ImageView status_e4;
    ImageView status_e5;

    Socket socket = SocketStatic.getSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String MEUS_ARCOS = getIntent().getStringExtra("MEUS_ARCOS");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_arco);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                socket.emit("ETAPA", ID_ARCO);
                socket.emit("ARCO", ID_ARCO);
            }
        });


        etapa1 = findViewById(R.id.card_e1);
        etapa2 = findViewById(R.id.card_e2);
        etapa3 = findViewById(R.id.card_e3);
        etapa4 = findViewById(R.id.card_e4);
        etapa5 = findViewById(R.id.card_e5);

        status = findViewById(R.id.id_arco_status);
        data_hora = findViewById(R.id.id_arco_data_hora);
        nome_equipe = findViewById(R.id.id_arco_nome_equipe);
        nome_tematica = findViewById(R.id.id_arco_nome_tematica);
        foto_lider = findViewById(R.id.id_arco_foto_lider);

        status_e1 = findViewById(R.id.id_arco_status_e1);
        status_e2 = findViewById(R.id.id_arco_status_e2);
        status_e3 = findViewById(R.id.id_arco_status_e3);
        status_e4 = findViewById(R.id.id_arco_status_e4);
        status_e5 = findViewById(R.id.id_arco_status_e5);

        socket.emit("ETAPA", ID_ARCO);
        socket.emit("ARCO", ID_ARCO);


        socket.on("ARCO".concat(ID_ARCO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ArcoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        swipeRefreshLayout.setRefreshing(false);

                        try {

                            JSONObject object = new JSONObject(result);

                            String str_status = "";
                            if(object.getString("SITUACAO").equals("1")){
                                str_status = "Em Desenvolvimento";
                            }else if(object.getString("SITUACAO").equals("2")){
                                str_status = "Finalizado";
                            }

                            status.setText("Status: " + str_status);
                            data_hora.setText("Criando em: " + object.getString("DATA_HORA"));
                            nome_equipe.setText("Equipe: " + object.getString("NOME_EQUIPE"));
                            nome_tematica.setText("Temática: " + object.getString("NOME_TEMATICA"));
                            Picasso.get().load(URL_BASE + "/IMG/" + object.getString("ID_LIDER") + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(foto_lider);


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });

        socket.on("ETAPA".concat(ID_ARCO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ArcoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        swipeRefreshLayout.setRefreshing(false);

                        try {

                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            for (int i = 0; i < size; i++) {

                                JSONObject object = array.getJSONObject(i);
                                if (object.getString("CODIGO_ETAPA").equals("1")) {
                                    definirIconImageView(status_e1, object.getString("SITUACAO_ETAPA"));
                                } else if (object.getString("CODIGO_ETAPA").equals("2")) {
                                    definirIconImageView(status_e2, object.getString("SITUACAO_ETAPA"));
                                } else if (object.getString("CODIGO_ETAPA").equals("3")) {
                                    definirIconImageView(status_e3, object.getString("SITUACAO_ETAPA"));
                                } else if (object.getString("CODIGO_ETAPA").equals("4")) {
                                    definirIconImageView(status_e4, object.getString("SITUACAO_ETAPA"));
                                } else if (object.getString("CODIGO_ETAPA").equals("5")) {
                                    definirIconImageView(status_e5, object.getString("SITUACAO_ETAPA"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                });
            }
        });


    }

    public static void definirIconImageView(ImageView imageView, String status) {
        if (status.equals("1")) {
            imageView.setImageResource(R.mipmap.ic_editw);
        } else if (status.equals("2")) {
            imageView.setImageResource(R.mipmap.ic_okw);
        } else if (status.equals("3")) {
            imageView.setImageResource(R.mipmap.ic_blockw);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArcoActivity.this, MenuActivity.class));
        finish();
    }

}
