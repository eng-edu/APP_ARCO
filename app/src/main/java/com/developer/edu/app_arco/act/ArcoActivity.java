package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerEquipe;
import com.developer.edu.app_arco.controller.ControllerSolicitacao;
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

    TextView codigo_equipe;
    TextView status;
    TextView data_hora;
    TextView nome_tematica;
    ImageView foto_lider;
    ImageView foto_menbro;

    ImageView status_e1;
    ImageView status_e2;
    ImageView status_e3;
    ImageView status_e4;
    ImageView status_e5;

    LinearLayout excuir;
    LinearLayout equipe;
    LinearLayout solicitacoes;

    TextView num_solicitacao;

    Socket socket = SocketStatic.getSocket();

    String CODIGO_EQUIPE = "";
    String ID_LIDER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String TIPO = sharedPreferences.getString("TIPO", "");

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

        codigo_equipe = findViewById(R.id.id_arco_codigo);
        status = findViewById(R.id.id_arco_status);
        data_hora = findViewById(R.id.id_arco_data_hora);
        nome_tematica = findViewById(R.id.id_arco_nome_tematica);
        foto_lider = findViewById(R.id.id_arco_foto_lider);
        foto_menbro = findViewById(R.id.id_arco_foto_menbro);

        status_e1 = findViewById(R.id.imageE1);
        status_e2 = findViewById(R.id.imageE2);
        status_e3 = findViewById(R.id.imageE3);
        status_e4 = findViewById(R.id.imageE4);
        status_e5 = findViewById(R.id.imageE5);

        excuir = findViewById(R.id.id_arco_layout_excluir);
        equipe = findViewById(R.id.id_arco_layout_equipe);
        solicitacoes = findViewById(R.id.id_arco_layout_solicitacoes);

        num_solicitacao = findViewById(R.id.id_arco_num_solicitacoes);


        socket.emit("ETAPA", ID_ARCO);
        socket.emit("ARCO", ID_ARCO);


        if(TIPO.equals("2")){
            foto_menbro.setVisibility(View.VISIBLE);
            Picasso.get().load(URL_BASE + "/IMG/" + ID_USUARIO + "_usuario.jpg").into(foto_menbro);
        }else {
            foto_menbro.setVisibility(View.GONE);
        }


        foto_lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, PerfilActivity.class);
                intent.putExtra("MEU_PERFIL", "N");
                intent.putExtra("ID_USUARIO", ID_LIDER);
                startActivity(intent);
            }
        });

        foto_menbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, PerfilActivity.class);
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
            }
        });


        equipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerEquipe.buscarEquipe(ArcoActivity.this, inflater, CODIGO_EQUIPE, ID_LIDER);
            }
        });

        solicitacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerSolicitacao.buscarSolicitacoes(ArcoActivity.this, inflater, CODIGO_EQUIPE);
            }
        });


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

                            ID_LIDER = object.getString("ID_LIDER");
                            CODIGO_EQUIPE = object.getString("CODIGO_EQUIPE");
                            String str_status = "";
                            if (object.getString("SITUACAO").equals("1")) {
                                str_status = "Em Desenvolvimento";
                            } else if (object.getString("SITUACAO").equals("2")) {
                                str_status = "Finalizado";
                            }

                            codigo_equipe.setText("Código da equipe: " + CODIGO_EQUIPE);
                            status.setText("Status: " + str_status);
                            data_hora.setText("Criando em: " + object.getString("DATA_HORA"));
                            nome_tematica.setText("Temática: " + object.getString("NOME_TEMATICA"));
                            Picasso.get().load(URL_BASE + "/IMG/" + object.getString("ID_LIDER") + "_usuario.jpg").into(foto_lider);

                            if (ID_USUARIO.equals(object.getString("ID_LIDER"))) {
                                excuir.setVisibility(View.VISIBLE);
                                equipe.setVisibility(View.VISIBLE);
                                solicitacoes.setVisibility(View.VISIBLE);
                            } else {
                                excuir.setVisibility(View.GONE);
                                equipe.setVisibility(View.GONE);
                                solicitacoes.setVisibility(View.GONE);
                            }


                            socket.emit("NUM_SOLICITACAO", CODIGO_EQUIPE);
                            socket.on("NUM_SOLICITACAO".concat(CODIGO_EQUIPE), new Emitter.Listener() {
                                @Override
                                public void call(final Object... args) {
                                    ArcoActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String result = args[0].toString(); //aqui recebo o json do arco
                                            swipeRefreshLayout.setRefreshing(false);

                                            if (Integer.parseInt(result) > 0) {
                                                num_solicitacao.setVisibility(View.VISIBLE);
                                                num_solicitacao.setText(result);
                                            } else {
                                                num_solicitacao.setVisibility(View.GONE);
                                            }

                                        }
                                    });
                                }
                            });

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


        etapa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("MEUS_ARCOS", MEUS_ARCOS);
                intent.putExtra("ID_ARCO", ID_ARCO);
                intent.putExtra("CODIGO", "1");
                startActivity(intent);
                finish();
            }
        });

        etapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("MEUS_ARCOS", MEUS_ARCOS);
                intent.putExtra("ID_ARCO", ID_ARCO);
                intent.putExtra("CODIGO", "1");
                startActivity(intent);
                finish();
            }
        });
        etapa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("MEUS_ARCOS", MEUS_ARCOS);
                intent.putExtra("ID_ARCO", ID_ARCO);
                intent.putExtra("CODIGO", "1");
                startActivity(intent);
                finish();
            }
        });
        etapa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("MEUS_ARCOS", MEUS_ARCOS);
                intent.putExtra("ID_ARCO", ID_ARCO);
                intent.putExtra("CODIGO", "1");
                startActivity(intent);
                finish();
            }
        });
        etapa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("MEUS_ARCOS", MEUS_ARCOS);
                intent.putExtra("ID_ARCO", ID_ARCO);
                intent.putExtra("CODIGO", "1");
                startActivity(intent);
                finish();
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
