package com.developer.edu.app_arco.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.util.Util;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.model.Etapa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ArcoActivity extends AppCompatActivity {

    CardView etapa1;
    CardView etapa2;
    CardView etapa3;
    CardView etapa4;
    CardView etapa5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        getIntent().getStringExtra("MEUS_ARCOS");


        etapa1 = findViewById(R.id.card_e1);
        etapa2 = findViewById(R.id.card_e2);
        etapa3 = findViewById(R.id.card_e3);
        etapa4 = findViewById(R.id.card_e4);
        etapa5 = findViewById(R.id.card_e5);


    }

    public static void definirIconImageView(ImageView imageView, String status) {

        if (status.equals("0")) {
            imageView.setImageResource(R.mipmap.ic_blockw);
        } else if (status.equals("1")) {
            imageView.setImageResource(R.mipmap.ic_editw);
        } else if (status.equals("2")) {
            imageView.setImageResource(R.mipmap.ic_clockw);
        } else if (status.equals("3")) {
            imageView.setImageResource(R.mipmap.ic_okw);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ArcoActivity.this, MenuActivity.class));
        finish();
    }

}
