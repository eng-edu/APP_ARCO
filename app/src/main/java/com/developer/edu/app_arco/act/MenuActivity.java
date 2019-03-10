package com.developer.edu.app_arco.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.controller.ControllerArco;
import com.developer.edu.app_arco.controller.ControllerEquipe;
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


        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String TIPO_USUARIO = sharedPreferences.getString("TIPO", "");
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        if (TIPO_USUARIO.equals("2")) {

        }

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);

                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
            }
        });
        novoArco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                if (TIPO_USUARIO.equals("1")) {
                    ControllerTematica.bucarTematicas(MenuActivity.this, inflater);
                } else if (TIPO_USUARIO.equals("2")) {
                    exibirMensagemEdt(ID_USUARIO);
                }


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


    }

    public void exibirMensagemEdt(final String ID_USUARIO) {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setMessage("Solicite o codigo da equipe ao seu líder!");
        // DECLARACAO DO EDITTEXT
        final EditText input = new EditText(this);
        mensagem.setView(input);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        mensagem.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mensagem.setPositiveButton("Entrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ControllerEquipe.novoArco(MenuActivity.this, input.getText().toString(), ID_USUARIO);
            }
        });

        mensagem.show();
        // FORÇA O TECLADO APARECER AO ABRIR O ALERT
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
