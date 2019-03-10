package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerPerfil;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PerfilActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_perfil);

        final String ID_USUARIO = getIntent().getStringExtra("ID_USUARIO");
        final String MEU_PERFIL = getIntent().getStringExtra("MEU_PERFIL");

        ControllerPerfil.buscarPerfil(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);
        ControllerPerfil.buscarEscolaridade(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ControllerPerfil.buscarPerfil(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);
                ControllerPerfil.buscarEscolaridade(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);

            }
        });

        LinearLayout editarPerfil = findViewById(R.id.id_perfil_editar_perfil);
        LinearLayout editarEscolaridade = findViewById(R.id.id_perfil_editar_escolaridade);
        TextView sair = findViewById(R.id.id_perfil_sair);

        if (MEU_PERFIL.equals("S")) {
            editarPerfil.setVisibility(View.VISIBLE);
            editarEscolaridade.setVisibility(View.VISIBLE);
            sair.setVisibility(View.VISIBLE);
        } else {
            editarPerfil.setVisibility(View.GONE);
            editarEscolaridade.setVisibility(View.GONE);
            sair.setVisibility(View.GONE);
        }
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                final String ID_USUARIO = sharedPreferences.getString("ID", "");
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
                finish();
            }
        });


        editarEscolaridade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilActivity.this, EditarEscolaridadeActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                final String ID_USUARIO = sharedPreferences.getString("ID", "");
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
                finish();
            }
        });


        final LinearLayout online = findViewById(R.id.id_perfil_layout_online);

        SocketStatic.getSocket().on("ON".concat(ID_USUARIO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                PerfilActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        if (result.equals("1")) {
                            online.setVisibility(View.VISIBLE);
                        } else if (result.equals("0")) {
                            online.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        SocketStatic.getSocket().on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                PerfilActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        online.setVisibility(View.GONE);
                    }
                });
            }
        });



        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                final SharedPreferences.Editor editor = getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });


    }


}
