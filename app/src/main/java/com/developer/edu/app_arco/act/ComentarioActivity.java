package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterComentario;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Comentario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ComentarioActivity extends AppCompatActivity {

    Socket socket = SocketStatic.getSocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        final ListView listView = findViewById(R.id.id_comentario_lista_comentarios);
        final AdapterComentario adapterComentario = new AdapterComentario(ComentarioActivity.this, new ArrayList<Comentario>());
        final EditText input = findViewById(R.id.id_comentario_input);
        Button enviar = findViewById(R.id.id_comentario_enviar);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final String TIPO = sharedPreferences.getString("TIPO", "");

        final String ID_OPINIAO = getIntent().getStringExtra("ID_OPINIAO");

        socket.emit("COMENTARIO", ID_OPINIAO);
        socket.on("COMENTARIO".concat(ID_OPINIAO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ComentarioActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();

                        List<Comentario> comentarios = new ArrayList<>();

                        try {
                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            for (int i = 0; i < size; i++) {

                                JSONObject object = array.getJSONObject(i);
                                comentarios.add(new Comentario(
                                        object.getString("ID_AUTOR"),
                                        object.getString("ID_LIDER"),
                                        object.getString("DATA_HORA"),
                                        object.getString("TEXTO")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterComentario.clear();
                        adapterComentario.addAll(comentarios);
                        listView.setAdapter(adapterComentario);


                    }
                });
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (TIPO.equals("1")) {
                    Intent intent = new Intent(ComentarioActivity.this, PerfilActivity.class);
                    intent.putExtra("MEU_PERFIL", "N");
                    intent.putExtra("ID_USUARIO", adapterComentario.getItem(position).getID_AUTOR());
                    startActivity(intent);
                }

                return false;
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input.getText().length() > 0) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("ID_USUARIO", ID_USUARIO);
                        object.put("ID_OPINIAO", ID_OPINIAO);
                        object.put("TEXTO", input.getText().toString());
                        socket.emit("MEU_COMENTARIO", object);
                        socket.emit("COMENTARIO", ID_OPINIAO);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    input.setText("");
                }


            }
        });


    }
}
