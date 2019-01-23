package com.developer.edu.app_arco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.controller.ControllerTematica;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MenuActivity extends AppCompatActivity {

    public Socket socket;

    {
        try {
            socket = IO.socket(ConfigRetrofit.URL_BASE);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SocketStatic.setSocket(socket);

        Button perfil = findViewById(R.id.id_menu_perfil);
        Button dicas = findViewById(R.id.id_menu_dicas);
        Button novoArco = findViewById(R.id.id_menu_novoarco);
        Button meusArcos = findViewById(R.id.id_menu_meusarcos);
        Button arcosCompartilhados = findViewById(R.id.id_menu_arcoscompartilhados);
        Button ranking = findViewById(R.id.id_menu_ranking);
        Button premioMes = findViewById(R.id.id_menu_premio);
        Button novaTematica = findViewById(R.id.id_menu_novatematica);
        Button premium = findViewById(R.id.id_menu_premium);
        Button sair = findViewById(R.id.id_menu_sair);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PerfilActivity.class).putExtra("meu_perfil", "S"));
            }
        });
        novoArco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerTematica.bucarTematicas(MenuActivity.this, inflater);
            }
        });
        meusArcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerArco.bucarMeusArco(MenuActivity.this, inflater);
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                final SharedPreferences.Editor editor = getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });



    }
}
