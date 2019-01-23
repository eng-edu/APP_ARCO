package com.developer.edu.app_arco;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.developer.edu.app_arco.controller.ControllerEquipe;

public class EquipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        ;

        FloatingActionButton novoMenbro = findViewById(R.id.id_equipe_novomenbro);
        novoMenbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                ControllerEquipe.bucarUsuarios(EquipeActivity.this, inflater, getIntent().getStringExtra("ID_ARCO"));
            }
        });

    }
}
