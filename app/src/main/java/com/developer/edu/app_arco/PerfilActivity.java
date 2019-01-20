package com.developer.edu.app_arco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        TextView pontos = findViewById(R.id.id_perfil_pontos);
        ImageView fotoperfil = findViewById(R.id.Id_perfil_image_perfil);
        final EditText nome = findViewById(R.id.id_nome_perfil);
        final EditText idade = findViewById(R.id.id_idade_perfil);
        final RadioButton sexoM = findViewById(R.id.id_perfil_sexo_m);
        final RadioButton sexoF = findViewById(R.id.id_perfil_sexo_f);
        final EditText escolaridade = findViewById(R.id.id_perfil_escolaridade);
        final EditText email = findViewById(R.id.id_email_perfil);
        final Button alterar = findViewById(R.id.id_perfil_alterar);

        //BUSCA NO SERVER O PERFIL .........

        nome.setEnabled(false);
        idade.setEnabled(false);
        sexoM.setEnabled(false);
        sexoF.setEnabled(false);
        escolaridade.setEnabled(false);
        email.setEnabled(false);


        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click == 0) {
                    click = 1;

                    //alterar libera pra edicao
                    nome.setEnabled(true);
                    idade.setEnabled(true);
                    sexoM.setEnabled(true);
                    sexoF.setEnabled(true);
                    escolaridade.setEnabled(true);
                    email.setEnabled(true);
                    alterar.setText("SALVAR");

                } else if (click == 1) {

                    //salva as alterações no server...


                    //bloqueia as edições
                    nome.setEnabled(false);
                    idade.setEnabled(false);
                    sexoM.setEnabled(false);
                    sexoF.setEnabled(false);
                    escolaridade.setEnabled(false);
                    email.setEnabled(false);
                    alterar.setText("EDITAR");


                    click = 0;
                }
            }
        });

    }
}
