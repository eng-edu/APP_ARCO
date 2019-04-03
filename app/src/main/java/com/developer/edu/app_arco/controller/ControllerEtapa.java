package com.developer.edu.app_arco.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.ArcoActivity;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEtapa {


    static AlertDialog alert;

    public static void buscarEtapa(final View view, final String ID_ETAPA, final String TIPO, final SwipeRefreshLayout swipeRefreshLayout) {

        final TextView nome_etapa = view.findViewById(R.id.id_etapa_nome_etapa);
        final TextView descricao_etapa = view.findViewById(R.id.id_etapa_descricao_etapa);


        Call<String> stringCall = ConfigRetrofit.getService().buscarEtapa(ID_ETAPA);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject object = new JSONObject(response.body());

                        nome_etapa.setText(object.getString("NOME_ETAPA"));


                        if (object.getString("CODIGO_ETAPA").equals("1")) {
                            descricao_etapa.setText(R.string.obs_realidade);
                        } else if (object.getString("CODIGO_ETAPA").equals("2")) {
                            descricao_etapa.setText(R.string.pto_chaves);
                        } else if (object.getString("CODIGO_ETAPA").equals("3")) {
                            descricao_etapa.setText(R.string.teorizacao);
                        } else if (object.getString("CODIGO_ETAPA").equals("4")) {
                            descricao_etapa.setText(R.string.h_solucao);
                        } else if (object.getString("CODIGO_ETAPA").equals("5")) {
                            descricao_etapa.setText(R.string.a_realidade);
                        }


                        swipeRefreshLayout.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else if (response.code() == 203) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public static void escreverOpiniao(final Context context, final LayoutInflater inflater, final String OPINIAO, final String ID_USUSARIO, final String ID_ETAPA) {

        final Socket socket = SocketStatic.getSocket();

        final View view = inflater.inflate(R.layout.dialog_opiniao, null);

        final EditText texto = view.findViewById(R.id.id_dialog_opiniao_texto);
        texto.setText(OPINIAO);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                Call<String> stringCall = ConfigRetrofit.getService().atualizarOpiniao(ID_USUSARIO, ID_ETAPA, texto.getText().toString());
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            alert.dismiss();
                            socket.emit("OPINIAO", ID_ETAPA);
                        } else if (response.code() == 203) {
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert = builder.create();
        alert.show();

    }

    public static void buscarOpiniao(final Context context, final LayoutInflater inflater, final String ID_USUARIO, final String ID_ETAPA) {

        Call<String> stringCall = ConfigRetrofit.getService().buscarOpiniao(ID_USUARIO, ID_ETAPA);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject object = new JSONObject(response.body());
                        escreverOpiniao(context, inflater, object.getString("TEXTO"), ID_USUARIO, ID_ETAPA);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 203) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static void finalizarEtapa(final Context context, String ID_ETAPA, final String CODIGO_ETAPA, final String ID_ARCO, final String MEUS_ARCOS, final String ID_USUARIO) {


        Call<String> stringCall = ConfigRetrofit.getService().finalizarEtapa(ID_ETAPA, CODIGO_ETAPA);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", ID_ARCO).putExtra("MEUS_ARCOS", MEUS_ARCOS));
                    ((Activity) context).finish();

                    Socket socket = SocketStatic.getSocket();

                    try {

                        JSONObject object1 = new JSONObject();
                        object1.put("ID_USUARIO", ID_USUARIO);
                        object1.put("CODIGO_ETAPA", CODIGO_ETAPA);
                        socket.emit("ESPECIALIDADE", object1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else if (response.code() == 203) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
