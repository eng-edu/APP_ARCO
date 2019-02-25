package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.controller.ControllerPerfil;

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
        LinearLayout editarFormacao = findViewById(R.id.id_perfil_editar_formacao);

        if (MEU_PERFIL.equals("S")) {
            editarPerfil.setVisibility(View.VISIBLE);
            editarFormacao.setVisibility(View.VISIBLE);
        } else {
            editarPerfil.setVisibility(View.GONE);
            editarFormacao.setVisibility(View.GONE);
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

    }

}
