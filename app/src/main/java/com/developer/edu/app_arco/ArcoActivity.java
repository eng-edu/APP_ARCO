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

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ArcoActivity extends AppCompatActivity {

    TextView tematica;
    TextView pontos;
    TextView curtidas;
    ImageView imtitulo;
    EditText edtitulo;
    Button btntitulo;
    ImageView lider;
    ImageView equipe;
    ImageView etapas;
    ImageView gostei;
    ImageView denuncia;

    Socket socket = SocketStatic.getSocket();
    String id_lider = "";
    int clickEditar = 0;
    int clickGostei = 0;



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

        tematica = findViewById(R.id.id_arco_tematica);
        pontos = findViewById(R.id.id_arco_ponto);
        curtidas = findViewById(R.id.id_arco_curtidas);
        imtitulo = findViewById(R.id.id_icon_titulo);
        edtitulo = findViewById(R.id.id_arco_titulo);
        btntitulo = findViewById(R.id.id_arco_alterar_salvar_titulo);
        lider = findViewById(R.id.id_arco_lider);
        equipe = findViewById(R.id.id_arco_equipe);
        etapas = findViewById(R.id.id_arco_etapas);
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
                    socket.emit("ARCO", object);
                }
            }
        });


        gostei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickGostei == 1) {
                    socket.emit("NGOSTEI", object);
                    socket.emit("ARCO", object);
                } else if (clickGostei == 2){
                    socket.emit("GOSTEI", object);
                    socket.emit("ARCO", object);
                }

            }
        });

        lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArcoActivity.this, PerfilActivity.class));
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

                            if (object.getString("EU_GOSTEI").equals("S")) {
                                gostei.setImageResource(R.mipmap.ic_gostei);
                                clickGostei = 1;
                            } else if (object.getString("EU_GOSTEI").equals("N")) {
                                gostei.setImageResource(R.mipmap.ic_ngostei);
                                clickGostei = 2;
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


}
