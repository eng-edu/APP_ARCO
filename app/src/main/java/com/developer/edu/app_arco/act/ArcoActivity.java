package com.developer.edu.app_arco.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.util.Util;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.model.Etapa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ArcoActivity extends AppCompatActivity {

    TextView tematica;
    TextView pontos;
    TextView curtidas;
    EditText edtitulo;
    public static ImageView btntitulo;
    ImageView lider;
    ImageView equipe;
    CardView etapa1;
    CardView etapa2;
    CardView etapa3;
    CardView etapa4;
    CardView etapa5;
    ImageView gostei;
    ImageView denuncia;
    ImageView comentario;


    ImageView backE1;
    ImageView backE2;
    ImageView backE3;
    ImageView backE4;
    ImageView backE5;

    Socket socket = SocketStatic.getSocket();
    int clickEditar = 1;
    int clickGostei = 1;
    String id_lider = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        getIntent().getStringExtra("MEUS_ARCOS");


        if (Util.verificarConexaoSocket(socket, ArcoActivity.this)) {
            socket.emit("ARCO", getIntent().getStringExtra("ID_ARCO"));
            socket.emit("ETAPA", getIntent().getStringExtra("ID_ARCO"));
        }

        tematica = findViewById(R.id.id_arco_tematica);
        pontos = findViewById(R.id.id_arco_ponto);
        curtidas = findViewById(R.id.id_arco_curtidas);

        edtitulo = findViewById(R.id.id_arco_titulo);
        btntitulo = findViewById(R.id.id_arco_alterar_salvar_titulo);
        lider = findViewById(R.id.id_arco_lider);
        equipe = findViewById(R.id.id_arco_equipe);
        etapa1 = findViewById(R.id.card_e1);
        etapa2 = findViewById(R.id.card_e2);
        etapa3 = findViewById(R.id.card_e3);
        etapa4 = findViewById(R.id.card_e4);
        etapa5 = findViewById(R.id.card_e5);
        gostei = findViewById(R.id.id_arco_gostei);
        denuncia = findViewById(R.id.id_arco_denuncia);
        comentario = findViewById(R.id.id_arco_comentario);

        backE1 = findViewById(R.id.imageE1);
        backE2 = findViewById(R.id.imageE2);
        backE3 = findViewById(R.id.imageE3);
        backE4 = findViewById(R.id.imageE4);
        backE5 = findViewById(R.id.imageE5);


        if(getIntent().getStringExtra("MEUS_ARCOS").equals("S")){
            btntitulo.setVisibility(View.VISIBLE);
        }else {
            btntitulo.setVisibility(View.GONE);
        }


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        edtitulo.setEnabled(false);
        btntitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEditar == 1) {
                    edtitulo.setEnabled(true);
                    btntitulo.setImageResource(R.mipmap.ic_save);
                    clickEditar = 2;
                } else if (clickEditar == 2) {
                    edtitulo.setEnabled(false);
                    btntitulo.setImageResource(R.mipmap.ic_edit);
                    clickEditar = 1;

                    final JSONObject jsontitulo = new JSONObject();
                    try {
                        jsontitulo.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                        jsontitulo.put("TITULO", edtitulo.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (Util.verificarConexaoSocket(socket, ArcoActivity.this)) {
                        socket.emit("TITULO", jsontitulo);
                        socket.emit("ARCO", getIntent().getStringExtra("ID_ARCO"));
                    }
                }
            }
        });


        final JSONObject jsongostei = new JSONObject();
        try {
            jsongostei.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
            jsongostei.put("ID_USUARIO", ID_USUARIO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gostei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clickGostei == 1) {

                    if (Util.verificarConexaoSocket(socket, ArcoActivity.this)) {
                        socket.emit("GOSTEI", jsongostei);
                    }


                    gostei.setImageResource(R.mipmap.ic_gostei);
                    clickGostei = 2;
                } else if (clickGostei == 2) {
                    socket.emit("NGOSTEI", jsongostei);
                    gostei.setImageResource(R.mipmap.ic_ngostei);
                    clickGostei = 1;
                }

                if (Util.verificarConexaoSocket(socket, ArcoActivity.this)) {
                    socket.emit("ARCO", getIntent().getStringExtra("ID_ARCO"));
                }


            }
        });

        lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, PerfilActivity.class);
                intent.putExtra("ID_USUARIO", id_lider);
                startActivity(intent);
            }
        });

        equipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EquipeActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                startActivity(intent);

            }
        });


        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mensagem = new AlertDialog.Builder(ArcoActivity.this);
                mensagem.setTitle("Denúncia");
                mensagem.setMessage("Descreva o motivo...");
                // DECLARACAO DO EDITTEXT
                final EditText input = new EditText(ArcoActivity.this);
                mensagem.setView(input);
                mensagem.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ControllerArco.denunciarArco(ArcoActivity.this, ID_USUARIO, getIntent().getStringExtra("ID_ARCO"),input.getText().toString());
                    }
                });

                mensagem.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mensagem.show();
            }
        });


        comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ArcoActivity.this, ComentarioActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                startActivity(intent);
                finish();
            }
        });


        etapa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                intent.putExtra("ID_LIDER", id_lider);
                intent.putExtra("CODIGO_ETAPA", "0");
                startActivity(intent);
                finish();
            }
        });
        etapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                intent.putExtra("ID_LIDER", id_lider);
                intent.putExtra("CODIGO_ETAPA", "1");
                startActivity(intent);
                finish();
            }
        });
        etapa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                intent.putExtra("ID_LIDER", id_lider);
                intent.putExtra("CODIGO_ETAPA", "2");
                startActivity(intent);
                finish();
            }
        });
        etapa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {      final Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                intent.putExtra("ID_LIDER", id_lider);

                intent.putExtra("CODIGO_ETAPA", "3");
                startActivity(intent);
                finish();
            }
        });
        etapa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                intent.putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS"));
                intent.putExtra("ID_LIDER", id_lider);
                intent.putExtra("CODIGO_ETAPA", "4");
                startActivity(intent);
                finish();

            }
        });


        //quando ouver uam alteração o server vai atulizar
        socket.on("ARCO".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ArcoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        try {
                            JSONObject object = new JSONObject(result);
                            tematica.setText(object.getString("TITULO_TEMATICA"));
                            pontos.setText(object.getString("PONTO")+" pontos");
                            curtidas.setText(object.getString("GOSTEI") + " curtidas");
                            edtitulo.setText(object.getString("TITULO_ARCO"));
                            edtitulo.setSelection(edtitulo.getText().length());
                            id_lider = object.getString("ID_LIDER");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        socket.on("ETAPA".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ArcoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String result = args[0].toString(); //aqui recebo o json do arco

                        try {
                            JSONArray array = new JSONArray(result);
                            List<Etapa> etapaList = new ArrayList<>();
                            int size = array.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject object = array.getJSONObject(i);
                                String codigo = object.getString("CODIGO");
                                String situacao = object.getString("SITUACAO");

                                if (codigo.equals("1")) {
                                    definirIconImageView(backE1, situacao);
                                } else if (codigo.equals("2")) {
                                    definirIconImageView(backE2, situacao);
                                } else if (codigo.equals("3")) {
                                    definirIconImageView(backE3, situacao);
                                } else if (codigo.equals("4")) {
                                    definirIconImageView(backE4, situacao);
                                } else if (codigo.equals("5")) {
                                    definirIconImageView(backE5, situacao);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        socket.emit("EU_GOSTEI", jsongostei);
        socket.on("EU_GOSTEI".concat(ID_USUARIO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ArcoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();

                        try {

                            JSONObject object = new JSONObject(result);
                            String eucurti_ = object.getString("EU_CURTI");
                            if (eucurti_.equals("1")) {
                                clickGostei = 2;
                                gostei.setImageResource(R.mipmap.ic_gostei);
                            } else {
                                clickGostei = 1;
                                gostei.setImageResource(R.mipmap.ic_ngostei);
                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    public static void definirIconImageView(ImageView imageView, String status) {

        if (status.equals("0")) {
            imageView.setImageResource(R.mipmap.ic_blockw);
        } else if (status.equals("1")) {
            imageView.setImageResource(R.mipmap.ic_editw);
        } else if (status.equals("2")) {
            imageView.setImageResource(R.mipmap.ic_clockw);
        } else if (status.equals("3")) {
            imageView.setImageResource(R.mipmap.ic_okw);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArcoActivity.this, MenuActivity.class));
        finish();
    }

}
