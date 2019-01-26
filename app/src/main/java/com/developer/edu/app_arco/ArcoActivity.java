package com.developer.edu.app_arco;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.model.Arco;
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
    ImageView imtitulo;
    EditText edtitulo;
    public static Button btntitulo;
    ImageView lider;
    ImageView equipe;
    ImageView etapa1;
    ImageView etapa2;
    ImageView etapa3;
    ImageView etapa4;
    ImageView etapa5;
    ImageView gostei;
    ImageView denuncia;

    Socket socket = SocketStatic.getSocket();
    String id_lider = "";
    int clickEditar = 0;
    int clickGostei = 0;
    String soulider = "";
    String soumenbro = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        socket.connect();

        final JSONObject object = new JSONObject();
        try {
            object.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
            object.put("ID_USUARIO", ID_USUARIO);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.emit("ARCO", object);
        socket.emit("ETAPA", object);

        tematica = findViewById(R.id.id_arco_tematica);
        pontos = findViewById(R.id.id_arco_ponto);
        curtidas = findViewById(R.id.id_arco_curtidas);
        imtitulo = findViewById(R.id.id_icon_titulo);
        edtitulo = findViewById(R.id.id_arco_titulo);
        btntitulo = findViewById(R.id.id_arco_alterar_salvar_titulo);
        lider = findViewById(R.id.id_arco_lider);
        equipe = findViewById(R.id.id_arco_equipe);
        etapa1 = findViewById(R.id.id_arco_etapa1);
        etapa2 = findViewById(R.id.id_arco_etapa2);
        etapa3 = findViewById(R.id.id_arco_etapa3);
        etapa4 = findViewById(R.id.id_arco_etapa4);
        etapa5 = findViewById(R.id.id_arco_etapa5);
        gostei = findViewById(R.id.id_arco_gostei);
        denuncia = findViewById(R.id.id_arco_denuncia);

        edtitulo.setEnabled(false);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btntitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEditar == 0) {
                    edtitulo.setEnabled(true);
                    btntitulo.setText("SALVAR");

                    clickEditar = 1;
                } else if (clickEditar == 1) {
                    edtitulo.setEnabled(false);
                    btntitulo.setText("EDITAR");
                    clickEditar = 0;
                    socket.emit("TITULO", edtitulo.getText().toString());

                }
            }
        });

        gostei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickGostei == 1) {
                    socket.emit("NGOSTEI", object);
                } else if (clickGostei == 2){
                    socket.emit("GOSTEI", object);
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
                startActivity(new Intent(ArcoActivity.this, EquipeActivity.class).putExtra("ID_ARCO",getIntent().getStringExtra("ID_ARCO")));
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


        etapa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("CODIGO_ETAPA", "0");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                startActivity(intent);
                finish();
            }
        });
        etapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("CODIGO_ETAPA", "1");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                startActivity(intent);
                finish();
            }
        });
        etapa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("CODIGO_ETAPA", "2");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                startActivity(intent);
                finish();
            }
        });
        etapa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("CODIGO_ETAPA", "3");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                startActivity(intent);
                finish();
            }
        });
        etapa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArcoActivity.this, EtapaActivity.class);
                intent.putExtra("CODIGO_ETAPA", "4");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                intent.putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
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
                            tematica.setText("Tematica: "+object.getString("TITULO_TEMATICA"));
                            pontos.setText(object.getString("PONTO")+" pontos");
                            curtidas.setText(object.getString("GOSTEI") + " pessoas gostaram deste arco!");
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
                                soulider = object.getString("SOULIDER");
                                soumenbro = object.getString("SOUMENBRO");



                                if (codigo.equals("1")) {
                                    definirIconImageView(etapa1, situacao, soulider, soumenbro);
                                } else if (codigo.equals("2")) {
                                    definirIconImageView(etapa2, situacao, soulider, soumenbro);
                                } else if (codigo.equals("3")) {
                                    definirIconImageView(etapa3, situacao, soulider, soumenbro);
                                } else if (codigo.equals("4")) {
                                    definirIconImageView(etapa4, situacao, soulider, soumenbro);
                                } else if (codigo.equals("5")) {
                                    definirIconImageView(etapa5, situacao, soulider, soumenbro);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArcoActivity.this, MenuActivity.class));
        finish();
    }

    public static void definirIconImageView(ImageView imageView, String status, String soulider, String soumenbro) {

        if (soulider.equals("S")) {
            if (status.equals("0")) {
                imageView.setImageResource(R.mipmap.ic_etapa1);
                imageView.setClickable(false);
            } else if (status.equals("1")) {
                imageView.setImageResource(R.mipmap.ic_etapa2);
                imageView.setClickable(false);
            } else if (status.equals("2")) {
                imageView.setImageResource(R.mipmap.ic_etapa3);
                imageView.setClickable(true);
            } else if (status.equals("3")) {
                imageView.setImageResource(R.mipmap.ic_etapa4);
                imageView.setClickable(true);
            }
        } else if (soulider.equals("N") && soumenbro.equals("S")) {
            if (status.equals("0")) {
                imageView.setImageResource(R.mipmap.ic_etapa1);
                imageView.setClickable(false);
            } else if (status.equals("1")) {
                imageView.setImageResource(R.mipmap.ic_etapa2);
                imageView.setClickable(true);
            } else if (status.equals("2")) {
                imageView.setImageResource(R.mipmap.ic_etapa3);
                imageView.setClickable(false);
            } else if (status.equals("3")) {
                imageView.setImageResource(R.mipmap.ic_etapa4);
                imageView.setClickable(true);
            }
        } else if (soulider.equals("N") && soumenbro.equals("N")) {
            if (status.equals("0")) {
                imageView.setImageResource(R.mipmap.ic_etapa1);
                imageView.setClickable(false);
            } else if (status.equals("1")) {
                imageView.setImageResource(R.mipmap.ic_etapa2);
                imageView.setClickable(false);
            } else if (status.equals("2")) {
                imageView.setImageResource(R.mipmap.ic_etapa3);
                imageView.setClickable(false);
            } else if (status.equals("3")) {
                imageView.setImageResource(R.mipmap.ic_etapa4);
                imageView.setClickable(true);
            }

            btntitulo.setVisibility(View.GONE);
        }

    }


}
