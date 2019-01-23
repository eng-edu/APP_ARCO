package com.developer.edu.app_arco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.conectionAPI.SocketStatic;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        socket.connect();
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        JSONObject object = new JSONObject();
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
                            edtitulo.setText("Titulo: "+object.getString("TITULO_ARCO"));
                            id_lider = object.getString("ID_LIDER");

                            if (object.getString("EU_GOSTEI").equals("S")) {
                                gostei.setImageResource(R.mipmap.ic_gostei);
                            } else if (object.getString("EU_GOSTEI").equals("N")) {
                                gostei.setImageResource(R.mipmap.ic_ngostei);
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
