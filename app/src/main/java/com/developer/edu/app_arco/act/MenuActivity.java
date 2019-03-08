package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.controller.ControllerTematica;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CardView perfil = findViewById(R.id.card_meuperfil);
        CardView novoArco = findViewById(R.id.card_novoarco);
        CardView meusArcos = findViewById(R.id.card_meusarcos);
        CardView arcosCompartilhados = findViewById(R.id.card_compartilhados);
        CardView ranking = findViewById(R.id.card_ranking);
        CardView premioMes = findViewById(R.id.card_premiodomes);
//        Button sair = findViewById(R.id.id_menu_sair);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String tipo = sharedPreferences.getString("TIPO", "");

        if (tipo.equals("2")) {
            novoArco.setVisibility(View.GONE);
        }

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                final String ID_USUARIO = sharedPreferences.getString("ID", "");
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
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

        arcosCompartilhados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
              //  ControllerArco.bucarArcosCompartilhados(MenuActivity.this, inflater);
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
              //  ControllerArco.bucarRanking(MenuActivity.this, inflater);
            }
        });

        premioMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PremioActivity.class));
            }
        });

//        sair.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
//                final SharedPreferences.Editor editor = getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
//                editor.clear();
//                editor.apply();
//                finish();
//            }
//        });

    }

}
