package com.developer.edu.app_arco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PreCadastroActivity extends AppCompatActivity {

    int avanco = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cadastro);


        final String[] infos = new String[]
                {"METODOLOGIA ATIVA....",
                        "ARCO....",
                        "EDUCACAO SEXUAL...",
                        "LIDER....",
                        "MENBRO...",
                        "CADASTRE-SE COMO..."};



        final EditText texto = findViewById(R.id.id_precadastro_texto);
        final Button avancar = findViewById(R.id.id_precadastro_avancar);
        final Button lider = (Button) findViewById(R.id.id_precadastro_lider);
        final Button menbro = (Button) findViewById(R.id.id_precadastro_menbro);

        lider.setVisibility(View.INVISIBLE);
        menbro.setVisibility(View.INVISIBLE);

        texto.setText(infos[avanco]);
        avanco++;

        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (avanco == 1) {
                    texto.setText(infos[avanco]);
                    avanco++;
                } else if (avanco == 2) {
                    texto.setText(infos[avanco]);
                    avanco++;
                } else if (avanco == 3) {
                    texto.setText(infos[avanco]);
                    avanco++;
                } else if (avanco == 4) {
                    texto.setText(infos[avanco]);
                    avanco++;
                } else if (avanco == 5) {
                    texto.setText(infos[avanco]);
                    lider.setVisibility(View.VISIBLE);
                    menbro.setVisibility(View.VISIBLE);
                    avancar.setVisibility(View.INVISIBLE);
                }
            }
        });

        lider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreCadastroActivity.this, CadastroActivity.class);
                intent.putExtra("tipo", "1");
                startActivity(intent);
                finish();

            }
        });

        menbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PreCadastroActivity.this, CadastroActivity.class);
                intent.putExtra("tipo", "2");
                startActivity(intent);
                finish();

            }
        });

    }
}
