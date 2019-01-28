package com.developer.edu.app_arco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.conectionAPI.SocketStatic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EtapaActivity extends AppCompatActivity {

    Socket socket = SocketStatic.getSocket();

    public static int ponto_ = 0;
    int click_ediatar_salvar = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        socket.emit("ETAPA", getIntent().getStringExtra("ID_ARCO"));

        final ImageView estrela1 = findViewById(R.id.id_etapa_moeda1);
        final ImageView estrela2 = findViewById(R.id.id_etapa_moeda2);
        final ImageView estrela3 = findViewById(R.id.id_etapa_moeda3);
        final ImageView estrela4 = findViewById(R.id.id_etapa_moeda4);
        final ImageView estrela5 = findViewById(R.id.id_etapa_moeda5);
        final TextView titulo = findViewById(R.id.id_etapa_titulo);
        final TextView texto = findViewById(R.id.id_etapa_texto);
        final Button editar_sarvar = findViewById(R.id.id_etapa_editar);
        final Button finalizar = findViewById(R.id.id_etapa_finalizar);

        editar_sarvar.setVisibility(View.GONE);
        finalizar.setVisibility(View.GONE);
        estrela1.setClickable(false);
        estrela2.setClickable(false);
        estrela3.setClickable(false);
        estrela4.setClickable(false);
        estrela5.setClickable(false);

        final Intent intent = getIntent();


        socket.on("ETAPA".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                EtapaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String result = args[0].toString(); //aqui recebo o json do arco
                        try {

                            JSONObject object = new JSONArray(result).getJSONObject(Integer.parseInt(intent.getStringExtra("CODIGO_ETAPA")));
                            ponto_ = Integer.parseInt(object.getString("PONTO"));
                            definirIconPontos(ponto_, estrela1, estrela2, estrela3, estrela4, estrela5);
                            texto.setText(object.getString("TEXTO"));
                            titulo.setText(object.getString("TITULO"));
                            String situacao = object.getString("SITUACAO");
                            //É UM DOS MEUS ARCOS?
                            if (intent.getStringExtra("MEUS_ARCOS").equals("S")) {


                                //SOU O LIDER?
                                if (intent.getStringExtra("ID_LIDER").equals(ID_USUARIO)) {

                                    if (situacao.equals("2")) {
                                        finalizar.setVisibility(View.VISIBLE);
                                        estrela1.setClickable(true);
                                        estrela2.setClickable(true);
                                        estrela3.setClickable(true);
                                        estrela4.setClickable(true);
                                        estrela5.setClickable(true);
                                    }else {
                                        editar_sarvar.setVisibility(View.GONE);
                                        finalizar.setVisibility(View.GONE);
                                        estrela1.setClickable(false);
                                        estrela2.setClickable(false);
                                        estrela3.setClickable(false);
                                        estrela4.setClickable(false);
                                        estrela5.setClickable(false);
                                    }

                                } else {

                                    if (situacao.equals("1")) {
                                        editar_sarvar.setVisibility(View.VISIBLE);
                                        finalizar.setVisibility(View.VISIBLE);
                                        estrela1.setClickable(false);
                                        estrela2.setClickable(false);
                                        estrela3.setClickable(false);
                                        estrela4.setClickable(false);
                                        estrela5.setClickable(false);
                                    }else {
                                        editar_sarvar.setVisibility(View.GONE);
                                        finalizar.setVisibility(View.GONE);
                                        estrela1.setClickable(false);
                                        estrela2.setClickable(false);
                                        estrela3.setClickable(false);
                                        estrela4.setClickable(false);
                                        estrela5.setClickable(false);
                                    }

                                }

                            } else if (intent.getStringExtra("MEUS_ARCOS").equals("N")) {
                                editar_sarvar.setVisibility(View.GONE);
                                finalizar.setVisibility(View.GONE);
                                estrela1.setClickable(false);
                                estrela2.setClickable(false);
                                estrela3.setClickable(false);
                                estrela4.setClickable(false);
                                estrela5.setClickable(false);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



        texto.setEnabled(false);
        editar_sarvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click_ediatar_salvar == 1) {
                    click_ediatar_salvar = 2;
                    editar_sarvar.setText("SALVAR");
                    texto.setEnabled(true);


                } else if (click_ediatar_salvar == 2) {
                    click_ediatar_salvar = 1;
                    texto.setEnabled(false);
                    editar_sarvar.setText("EDITAR");


                    JSONObject jsonsalvar = new JSONObject();
                    try {

                        jsonsalvar.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                        int cod = Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA"));
                        String codigoetapa = String.valueOf(cod + 1);
                        jsonsalvar.put("CODIGO", codigoetapa);
                        jsonsalvar.put("TEXTO", texto.getText().toString());
                        socket.emit("SALVAR", jsonsalvar);
                        socket.emit("ETAPA", getIntent().getStringExtra("ID_ARCO"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intent.getStringExtra("ID_LIDER").equals(ID_USUARIO)) {


                    JSONObject jsonfinalizarlider = new JSONObject();
                    try {

                        jsonfinalizarlider.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                        int cod = Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA"));
                        String codigoetapa = String.valueOf(cod + 1);
                        jsonfinalizarlider.put("CODIGO", codigoetapa);
                        jsonfinalizarlider.put("PONTO", String.valueOf(ponto_));
                        socket.emit("FINALIZAR_LIDER", jsonfinalizarlider);
                        socket.emit("ETAPA", getIntent().getStringExtra("ID_ARCO"));
                        startActivity(new Intent(EtapaActivity.this, ArcoActivity.class).putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO")).putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS")));
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    JSONObject jsonsalvar = new JSONObject();
                    try {

                        jsonsalvar.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                        int cod = Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA"));
                        String codigoetapa = String.valueOf(cod + 1);
                        jsonsalvar.put("CODIGO", codigoetapa);
                        jsonsalvar.put("TEXTO", texto.getText().toString());
                        socket.emit("FINALIZAR_MENBRO", jsonsalvar);
                        socket.emit("ETAPA", getIntent().getStringExtra("ID_ARCO"));
                        startActivity(new Intent(EtapaActivity.this, ArcoActivity.class).putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO")).putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS")));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        estrela1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(1, estrela1, estrela2, estrela3, estrela4, estrela5);
            }
        });

        estrela2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(2, estrela1, estrela2, estrela3, estrela4, estrela5);
            }
        });

        estrela3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(3, estrela1, estrela2, estrela3, estrela4, estrela5);
            }
        });

        estrela4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(4, estrela1, estrela2, estrela3, estrela4, estrela5);
            }
        });

        estrela5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(5, estrela1, estrela2, estrela3, estrela4, estrela5);
            }
        });
    }


    public static void definirIconPontos(int ponto, ImageView estrela1, ImageView estrela2, ImageView estrela3, ImageView estrela4, ImageView estrela5) {

        if (ponto == 1) {
            estrela1.setImageResource(R.mipmap.ic_coin);
            estrela2.setImageResource(R.mipmap.ic_coin2);
            estrela3.setImageResource(R.mipmap.ic_coin2);
            estrela4.setImageResource(R.mipmap.ic_coin2);
            estrela5.setImageResource(R.mipmap.ic_coin2);
            ponto_ = ponto;
        } else if (ponto == 2) {
            estrela1.setImageResource(R.mipmap.ic_coin);
            estrela2.setImageResource(R.mipmap.ic_coin);
            estrela3.setImageResource(R.mipmap.ic_coin2);
            estrela4.setImageResource(R.mipmap.ic_coin2);
            estrela5.setImageResource(R.mipmap.ic_coin2);
            ponto_ = ponto;
        } else if (ponto == 3) {
            estrela1.setImageResource(R.mipmap.ic_coin);
            estrela2.setImageResource(R.mipmap.ic_coin);
            estrela3.setImageResource(R.mipmap.ic_coin);
            estrela4.setImageResource(R.mipmap.ic_coin2);
            estrela5.setImageResource(R.mipmap.ic_coin2);
            ponto_ = ponto;
        } else if (ponto == 4) {
            estrela1.setImageResource(R.mipmap.ic_coin);
            estrela2.setImageResource(R.mipmap.ic_coin);
            estrela3.setImageResource(R.mipmap.ic_coin);
            estrela4.setImageResource(R.mipmap.ic_coin);
            estrela5.setImageResource(R.mipmap.ic_coin2);
            ponto_ = ponto;
        } else if (ponto == 5) {
            estrela1.setImageResource(R.mipmap.ic_coin);
            estrela2.setImageResource(R.mipmap.ic_coin);
            estrela3.setImageResource(R.mipmap.ic_coin);
            estrela4.setImageResource(R.mipmap.ic_coin);
            estrela5.setImageResource(R.mipmap.ic_coin);
            ponto_ = ponto;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EtapaActivity.this, ArcoActivity.class).putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO")).putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS")));
        finish();
    }



}
