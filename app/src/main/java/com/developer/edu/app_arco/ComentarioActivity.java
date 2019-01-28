package com.developer.edu.app_arco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Comentario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ComentarioActivity extends AppCompatActivity {


    Socket socket = SocketStatic.getSocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        socket.emit("COMENTARIO", getIntent().getStringExtra("ID_ARCO"));

        final EditText comentario = findViewById(R.id.id_comentario_comenatrio);
        Button publicar = findViewById(R.id.id_comentario_publicar);
        final ListView comentarios = findViewById(R.id.id_lcomentario_lista);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");
        final ArrayAdapter<Comentario> arrayAdapter = new AdapterComentario(ComentarioActivity.this, new ArrayList<Comentario>());


        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
                Date data = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                String hora_atual = dateFormat_hora.format(cal.getTime());

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                String data_atual = sdf.format(date);

                final JSONObject jsoncomentario = new JSONObject();
                try {
                    jsoncomentario.put("ID_ARCO", getIntent().getStringExtra("ID_ARCO"));
                    jsoncomentario.put("ID_USUARIO", ID_USUARIO);
                    jsoncomentario.put("TEXTO", comentario.getText().toString());
                    jsoncomentario.put("DATA", data_atual);
                    jsoncomentario.put("HORA", hora_atual);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                socket.emit("POSTCOMENTARIO", jsoncomentario);
                socket.emit("COMENTARIO", getIntent().getStringExtra("ID_ARCO"));
                comentario.setText("");
            }
        });

        //quando ouver uam alteração o server vai atulizar
        socket.on("COMENTARIO".concat(getIntent().getStringExtra("ID_ARCO")), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ComentarioActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco

                        List<Comentario> comentarioList = new ArrayList<>();

                        try {

                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            for (int i = 0; i < size; i++) {

                                JSONObject object = array.getJSONObject(i);
                                comentarioList.add(new Comentario(
                                        object.getString("ID"),
                                        object.getString("EMAIL"),
                                        object.getString("TEXTO"),
                                        object.getString("DATA"),
                                        object.getString("HORA")
                                ));

                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(comentarioList);
                            comentarios.setAdapter(arrayAdapter);
                            comentarios.smoothScrollToPosition(arrayAdapter.getCount());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ComentarioActivity.this, ArcoActivity.class).putExtra("ID_ARCO", getIntent().getStringExtra("ID_ARCO")).putExtra("MEUS_ARCOS", getIntent().getStringExtra("MEUS_ARCOS")));
        finish();
    }

}
