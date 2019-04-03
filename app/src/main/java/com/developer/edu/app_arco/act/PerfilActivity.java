package com.developer.edu.app_arco.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerPerfil;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PerfilActivity extends AppCompatActivity {


    Socket socket = SocketStatic.getSocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_perfil);

        final String ID_USUARIO = getIntent().getStringExtra("ID_USUARIO");
        final String MEU_PERFIL = getIntent().getStringExtra("MEU_PERFIL");

        ControllerPerfil.buscarPerfil(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);
        ControllerPerfil.buscarEscolaridade(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ControllerPerfil.buscarPerfil(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);
                ControllerPerfil.buscarEscolaridade(getWindow().getDecorView(), ID_USUARIO, MEU_PERFIL.equals("S"), swipeRefreshLayout);

                try {

                    JSONObject object1 = new JSONObject();
                    object1.put("ID_USUARIO", ID_USUARIO);
                    object1.put("CODIGO_ETAPA", "1");
                    socket.emit("ESPECIALIDADE", object1);

                    JSONObject object2 = new JSONObject();
                    object2.put("ID_USUARIO", ID_USUARIO);
                    object2.put("CODIGO_ETAPA", "2");
                    socket.emit("ESPECIALIDADE", object2);

                    JSONObject object3 = new JSONObject();
                    object3.put("ID_USUARIO", ID_USUARIO);
                    object3.put("CODIGO_ETAPA", "3");
                    socket.emit("ESPECIALIDADE", object3);

                    JSONObject object4 = new JSONObject();
                    object4.put("ID_USUARIO", ID_USUARIO);
                    object4.put("CODIGO_ETAPA", "4");
                    socket.emit("ESPECIALIDADE", object4);

                    JSONObject object5 = new JSONObject();
                    object5.put("ID_USUARIO", ID_USUARIO);
                    object5.put("CODIGO_ETAPA", "5");
                    socket.emit("ESPECIALIDADE", object5);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        LinearLayout editarPerfil = findViewById(R.id.id_perfil_editar_perfil);
        LinearLayout editarEscolaridade = findViewById(R.id.id_perfil_editar_escolaridade);
        TextView sair = findViewById(R.id.id_perfil_sair);

        if (MEU_PERFIL.equals("S")) {
            editarPerfil.setVisibility(View.VISIBLE);
            editarEscolaridade.setVisibility(View.VISIBLE);
            sair.setVisibility(View.VISIBLE);
        } else {
            editarPerfil.setVisibility(View.GONE);
            editarEscolaridade.setVisibility(View.GONE);
            sair.setVisibility(View.GONE);
        }
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                final String ID_USUARIO = sharedPreferences.getString("ID", "");
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
                finish();
            }
        });


        editarEscolaridade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PerfilActivity.this, EditarEscolaridadeActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                final String ID_USUARIO = sharedPreferences.getString("ID", "");
                intent.putExtra("MEU_PERFIL", "S");
                intent.putExtra("ID_USUARIO", ID_USUARIO);
                startActivity(intent);
                finish();
            }
        });


        final LinearLayout online = findViewById(R.id.id_perfil_layout_online);

        SocketStatic.getSocket().on("ON".concat(ID_USUARIO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                PerfilActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco
                        if (result.equals("1")) {
                            online.setVisibility(View.VISIBLE);
                        } else if (result.equals("0")) {
                            online.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        SocketStatic.getSocket().on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                PerfilActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        online.setVisibility(View.GONE);
                    }
                });
            }
        });


        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                final SharedPreferences.Editor editor = getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });


        final TextView te1 = findViewById(R.id.id_perfil_consq_text_nivel_obs_realidade);
        final TextView te2 = findViewById(R.id.id_perfil_consq_text_nivel_pto_chaves);
        final TextView te3 = findViewById(R.id.id_perfil_consq_text_nivel_teorizacao);
        final TextView te4 = findViewById(R.id.id_perfil_consq_text_nivel_hipotestes_solucao);
        final TextView te5 = findViewById(R.id.id_perfil_consq_text_nivel_aplicao_realidade);

        final ImageView ie1 = findViewById(R.id.id_perfil_consq_img_nivel_obs_realidade);
        final ImageView ie2 = findViewById(R.id.id_perfil_consq_img_nivel_pto_chaves);
        final ImageView ie3 = findViewById(R.id.id_perfil_consq_img_nivel_teorizacao);
        final ImageView ie4 = findViewById(R.id.id_perfil_consq_img_nivel_hipotestes_solucao);
        final ImageView ie5 = findViewById(R.id.id_perfil_consq_img_nivel_aplicao_realidade);


        try {

            JSONObject object1 = new JSONObject();
            object1.put("ID_USUARIO", ID_USUARIO);
            object1.put("CODIGO_ETAPA", "1");
            socket.emit("ESPECIALIDADE", object1);

            JSONObject object2 = new JSONObject();
            object2.put("ID_USUARIO", ID_USUARIO);
            object2.put("CODIGO_ETAPA", "2");
            socket.emit("ESPECIALIDADE", object2);

            JSONObject object3 = new JSONObject();
            object3.put("ID_USUARIO", ID_USUARIO);
            object3.put("CODIGO_ETAPA", "3");
            socket.emit("ESPECIALIDADE", object3);

            JSONObject object4 = new JSONObject();
            object4.put("ID_USUARIO", ID_USUARIO);
            object4.put("CODIGO_ETAPA", "4");
            socket.emit("ESPECIALIDADE", object4);

            JSONObject object5 = new JSONObject();
            object5.put("ID_USUARIO", ID_USUARIO);
            object5.put("CODIGO_ETAPA", "5");
            socket.emit("ESPECIALIDADE", object5);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        socket.on("ESPECIALIDADE".concat(ID_USUARIO+"_1"), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                (PerfilActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            setarImgNivel(object.getString("NOME"), object.getString("NIVEL"), ie1, te1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        socket.on("ESPECIALIDADE".concat(ID_USUARIO+"_2"), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                (PerfilActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            setarImgNivel(object.getString("NOME"), object.getString("NIVEL"), ie2, te2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        socket.on("ESPECIALIDADE".concat(ID_USUARIO+"_3"), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                (PerfilActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            setarImgNivel(object.getString("NOME"), object.getString("NIVEL"), ie3, te3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        socket.on("ESPECIALIDADE".concat(ID_USUARIO+"_4"), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                (PerfilActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            setarImgNivel(object.getString("NOME"), object.getString("NIVEL"), ie4, te4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        socket.on("ESPECIALIDADE".concat(ID_USUARIO+"_5"), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                (PerfilActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        try {
                            JSONObject object = new JSONObject(result);
                            setarImgNivel(object.getString("NOME"), object.getString("NIVEL"), ie5, te5);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }


    public static void setarImgNivel(String nome, String nivel, ImageView imageView, TextView textView) {

        if (nome.equals("bronze")) {
            imageView.setImageResource(R.mipmap.ic_bronze);
            textView.setText("Bronze nível: " + nivel);
        } else if (nome.equals("prata")) {
            imageView.setImageResource(R.mipmap.ic_prata);
            textView.setText("Prata nível: " + nivel);
        } else if (nome.equals("ouro")) {
            imageView.setImageResource(R.mipmap.ic_ouro);
            textView.setText("Ouro nível: " + nivel);
        } else if (nome.equals("diamante")) {
            imageView.setImageResource(R.mipmap.ic_diamante);
            textView.setText("Diamante nível: " + nivel);
        }else if (nome.equals("iniciante")) {
            imageView.setImageResource(R.mipmap.ic_baby);
            textView.setText("Iniciante nível: " + nivel);
        }

    }

}
