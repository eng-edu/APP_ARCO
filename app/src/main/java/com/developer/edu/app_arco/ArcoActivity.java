package com.developer.edu.app_arco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ArcoActivity extends AppCompatActivity {

    TextView tematica;
    TextView pontos;
    TextView curtidas;
    ImageView imtitulo;
    EditText edtitulo;
    Button btntitulo;
    ImageView lider;
    ImageView equipe;
    ImageView etapas;
    ImageView gostei;
    ImageView denuncia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        tematica = findViewById(R.id.id_arco_tematica);
        pontos = findViewById(R.id.id_arco_ponto);
        curtidas = findViewById(R.id.id_arco_curtidas);
        imtitulo = findViewById(R.id.id_icon_titulo);
        edtitulo = findViewById(R.id.id_arco_titulo);
        btntitulo = findViewById(R.id.id_arco_alterar_salvar_titulo);
        lider = findViewById(R.id.id_arco_lider);
        equipe = findViewById(R.id.id_arco_equipe);
        etapas = findViewById(R.id.id_arco_etapas);
        gostei = findViewById(R.id.id_arco_gostei);
        denuncia = findViewById(R.id.id_arco_denuncia);



    }
}
