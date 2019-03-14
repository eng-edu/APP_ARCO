package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.edu.app_arco.R;

public class EtapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String TIPO = sharedPreferences.getString("TIPO", "");

        final String MEUS_ARCOS = getIntent().getStringExtra("MEUS_ARCOS");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");
        final String CODIGO = getIntent().getStringExtra("CODIGO");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        final String MEUS_ARCOS = getIntent().getStringExtra("MEUS_ARCOS");
        final String ID_ARCO = getIntent().getStringExtra("ID_ARCO");
        startActivity(new Intent(EtapaActivity.this, ArcoActivity.class).putExtra("ID_ARCO", ID_ARCO).putExtra("MEUS_ARCOS", MEUS_ARCOS));
        finish();
    }
}
