package com.developer.edu.app_arco.act;

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
import android.widget.TextView;

import com.developer.edu.app_arco.adapter.AdapterUsuario;
import com.developer.edu.app_arco.R;
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



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");
        final String CODIGO_EQUIPE = getIntent().getStringExtra("CODIGO_EQUIPE");

        TextView codigo = findViewById(R.id.id_equipe_codigo);
        codigo.setText(CODIGO_EQUIPE);


    }

}
