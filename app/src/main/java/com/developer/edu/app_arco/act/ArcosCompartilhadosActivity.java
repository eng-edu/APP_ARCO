package com.developer.edu.app_arco.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterOpiniao;
import com.developer.edu.app_arco.controller.ControllerArcosCompartilhados;
import com.developer.edu.app_arco.model.Opiniao;

import java.util.ArrayList;

public class ArcosCompartilhadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcos_compartilhados);


        final ListView listView = findViewById(R.id.id_lista_a_compartilhados);
        final AdapterOpiniao arrayAdapter = new AdapterOpiniao(ArcosCompartilhadosActivity.this, new ArrayList<Opiniao>());
        final LayoutInflater inflater = getLayoutInflater();

        ControllerArcosCompartilhados.buscarArcos(ArcosCompartilhadosActivity.this, inflater, arrayAdapter, listView);
    }
}
