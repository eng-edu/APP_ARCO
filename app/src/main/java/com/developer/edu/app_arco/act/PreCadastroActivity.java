package com.developer.edu.app_arco.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;

public class PreCadastroActivity extends AppCompatActivity {

    int avanco = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cadastro);


        final String[] infos = new String[]
                {"Metodologia ativa\n" +
                        "É um processo de aprendizagem onde a sua principal característica e fazer do aluno o maior responsável pelo seu aprendizado participando ativamente da construção do seu conhecimento onde se desenvolve sua capacidade de pesquisa, observação e uma visão mais crítica sobre o mundo.",
                        "Arco de Maguerez\n" +"É uma metodologia problematizadora que fornece um caminho para a atuação sobre os problemas da realidade. Uma ferramenta onde auxilia na aprendizagem do aluno, onde invés de uma aula mais ou menos passiva em que os alunos se limitariam a ouvir o professor, eles se tornam agentes ativos, deixam  de estar dependentes de alguém, neste caso de um professor, para se tornarem independentes na procura de soluções para os seus próprios problemas.\n" +
                                "A metodologia apresenta um esquema dividido em cinco etapas que começam e terminam na realidade, ou seja, o aluno trabalhará com informações reais direcionado a um projeto real no final. ",
                        "Educação Sexual\n" +
                                "É ensinar e esclarecer dúvidas sobre os aspectos cognitivos, físicos, emocionais e sociais da sexualidade, é preparar os adolescentes para que desenvolvam conhecimento, e valores éticos para realizar escolhas positivas e saudáveis sobre relacionamentos e colabora para o amadurecimento e evitar conflitos provenientes de dúvida e medos.",
                        "Líder\n" +
                                "E o mediador da relações de aprendizagem ele que irar orientar os membros a encontrar sentido naquilo que está aprendendo, o responsável por gerenciar os arcos e organizar os membros e avaliar cada etapa construída pelos membros.",
                        "Membro \n" +
                                "E o participante de um grupo responsável por alimentar a ferramenta durante as etapas do arco, ele que irá realizar toda a pesquisa para construção da etapas junto com outros membros e com orientação do líder.",
                        "CADASTRE-SE COMO?"};



        final TextView texto = findViewById(R.id.id_precadastro_texto);
        final ImageView avancar = findViewById(R.id.id_precadastro_avancar);
        final Button lider =  findViewById(R.id.id_precadastro_lider);
        final Button menbro = findViewById(R.id.id_precadastro_menbro);
        lider.setVisibility(View.GONE);
        menbro.setVisibility(View.GONE);

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
                    avancar.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PreCadastroActivity.this, LoginActivity.class));
        finish();
    }
}
