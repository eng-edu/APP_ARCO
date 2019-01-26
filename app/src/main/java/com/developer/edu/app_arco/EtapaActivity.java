package com.developer.edu.app_arco;

import android.content.Context;
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
    int click_ediatar_salvar = 0;
    String soulider = "";
    String soumenbro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);
        socket.connect();
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        final JSONObject object = new JSONObject();
        try {
            object.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
            object.put("ID_USUARIO", ID_USUARIO);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("ETAPA", object);


        final ImageView estrela1 = findViewById(R.id.id_etapa_moeda1);
        final ImageView estrela2 = findViewById(R.id.id_etapa_moeda2);
        final ImageView estrela3 = findViewById(R.id.id_etapa_moeda3);
        final ImageView estrela4 = findViewById(R.id.id_etapa_moeda4);
        final ImageView estrela5 = findViewById(R.id.id_etapa_moeda5);
        final TextView titulo = findViewById(R.id.id_etapa_titulo);
        final TextView texto = findViewById(R.id.id_etapa_texto);
        final Button editar_sarvar = findViewById(R.id.id_etapa_editar);
        final Button finalizar = findViewById(R.id.id_etapa_finalizar);

        texto.setEnabled(false);
        editar_sarvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click_ediatar_salvar == 0) {
                    click_ediatar_salvar = 1;
                    editar_sarvar.setText("SALVAR");
                    texto.setEnabled(true);


                    //emitir o texto salvar...

                } else if (click_ediatar_salvar == 1) {
                    click_ediatar_salvar = 0;

                    final JSONObject salvar = new JSONObject();
                    try {
                        salvar.put("ID_ARCO", Integer.parseInt(getIntent().getStringExtra("ID_ARCO")));
                        salvar.put("CODIGO",Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA"))+1);
                        salvar.put("TEXTO", texto.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    socket.emit("SALVAR", salvar);
                    socket.emit("ETAPA", object);

                    texto.setEnabled(false);
                    editar_sarvar.setText("EDITAR");
                    //emitir o texto mudar status...
                    socket.emit("ETAPA", object);
                }
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (soulider.equals("S")) {


                    final JSONObject salvar = new JSONObject();
                    try {
                        salvar.put("ID_ARCO", Integer.parseInt(getIntent().getStringExtra("ID_ARCO")));
                        salvar.put("CODIGO",Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA"))+1);
                        salvar.put("PONTO", PerfilActivity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    socket.emit("SALVAR", salvar);
                    socket.emit("ETAPA", object);

                    //emitir capturar os pontos mudar status...
                } else if (soumenbro.equals("S") && soulider.equals("N")) {
                    socket.emit("ETAPA", object);
                    //emitir capturar os pontos mudar status...
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


        socket.on("ETAPA".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                EtapaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String result = args[0].toString(); //aqui recebo o json do arco

                        try {

                            JSONArray array = new JSONArray(result);
                            JSONObject object = array.getJSONObject(Integer.parseInt(getIntent().getStringExtra("CODIGO_ETAPA")));
                            titulo.setText(object.getString("TITULO"));
                            definirIconPontos(Integer.parseInt(object.getString("PONTO")), estrela1, estrela2, estrela3, estrela4, estrela5);
                            soulider = object.getString("SOULIDER");
                            soumenbro = object.getString("SOUMENBRO");
                            texto.setText(object.getString("TEXTO"));

                            if (soulider.equals("S")) {
                                editar_sarvar.setVisibility(View.GONE);
                            } else if (soumenbro.equals("S")) {
                                estrela1.setClickable(false);
                                estrela2.setClickable(false);
                                estrela3.setClickable(false);
                                estrela4.setClickable(false);
                                estrela5.setClickable(false);
                            } else {
                                estrela1.setClickable(false);
                                estrela2.setClickable(false);
                                estrela3.setClickable(false);
                                estrela4.setClickable(false);
                                estrela5.setClickable(false);
                                finalizar.setVisibility(View.GONE);
                                editar_sarvar.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
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



}
