package com.developer.edu.app_arco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerEquipe;
import com.developer.edu.app_arco.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EquipeActivity extends AppCompatActivity {

    Socket socket = SocketStatic.getSocket();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);

        socket.connect();

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        final FloatingActionButton novoMenbro = findViewById(R.id.id_equipe_novomenbro);
        novoMenbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerEquipe.bucarUsuarios(EquipeActivity.this, inflater, getIntent().getStringExtra("ID_ARCO"), socket);
            }
        });


        if(getIntent().getStringExtra("MEUS_ARCOS").equals("S")){
            novoMenbro.setVisibility(View.VISIBLE);
        }else {
            novoMenbro.setVisibility(View.GONE);
        }

        final ListView listView = findViewById(R.id.id_lista_menbros);
        final AdapterUsuario arrayAdapter = new AdapterUsuario(EquipeActivity.this, new ArrayList<Usuario>());

        socket.emit("EQUIPE", getIntent().getStringExtra("ID_ARCO"));
        socket.on("EQUIPE".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                EquipeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            List<Usuario> usuarios = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                JSONObject object = array.getJSONObject(i);
                                usuarios.add(new Usuario(object.getString("ID"), object.getString("NOME"), object.getString("EMAIL")));
                            }
                            arrayAdapter.clear();
                            arrayAdapter.addAll(usuarios);
                            listView.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(EquipeActivity.this, PerfilActivity.class).putExtra("ID_USUARIO", arrayAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        socket.disconnect();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
