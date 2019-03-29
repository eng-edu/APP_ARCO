package com.developer.edu.app_arco.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterComentario;
import com.developer.edu.app_arco.model.Comentario;

import java.util.ArrayList;

public class ComentarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        ListView listView = findViewById(R.id.id_comentario_lista_comentarios);
        AdapterComentario adapterComentario = new AdapterComentario(ComentarioActivity.this, new ArrayList<Comentario>());
        EditText input = findViewById(R.id.id_comentario_input);
        Button enviar = findViewById(R.id.id_comentario_enviar);

        //id opiniao
        //id usuario

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
