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
import com.developer.edu.app_arco.act.EtapaActivity;
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

        final TextView situacao_etapa = view.findViewById(R.id.id_etapa_situacao);
        final TextView nome_tematica = view.findViewById(R.id.id_etapa_nome_tematica);
        final TextView descricao_temaica = view.findViewById(R.id.id_etapa_descricao_tematica);
        final TextView nome_etapa = view.findViewById(R.id.id_etapa_nome_etapa);
        final TextView descricao_etapa = view.findViewById(R.id.id_etapa_descricao_etapa);


        Call<String> stringCall = ConfigRetrofit.getService().buscarEtapa(ID_ETAPA);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject object = new JSONObject(response.body());

                        String situacao = "";
                        if (object.getString("SITUACAO_ETAPA").equals("1")) {
                            situacao = "Etapa em desenvolvimento";
                        } else if (object.getString("SITUACAO_ETAPA").equals("2")) {
                            situacao = "Etapa finalizada";
                        } else if (object.getString("SITUACAO_ETAPA").equals("3")) {
                            situacao = "Etapa bloaqueada";
                        }

                        situacao_etapa.setText(situacao);
                        nome_tematica.setText(object.getString("NOME_TEMATICA"));
                        descricao_temaica.setText(object.getString("DESCRICAO_TEMATICA"));
                        nome_etapa.setText(object.getString("NOME_ETAPA"));

                        if (TIPO.equals("1")) {
                            descricao_etapa.setText(object.getString("DESCRICAO_ETAPA_LIDER"));
                        } else {
                            descricao_etapa.setText(object.getString("DESCRICAO_ETAPA_MENBRO"));
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

    public static void buscarOpiniao(final Context context, final LayoutInflater inflater, final String ID_USUSARIO, final String ID_ETAPA) {

        Call<String> stringCall = ConfigRetrofit.getService().buscarOpiniao(ID_USUSARIO, ID_ETAPA);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject object = new JSONObject(response.body());
                        escreverOpiniao(context, inflater, object.getString("TEXTO"), ID_USUSARIO, ID_ETAPA);

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

    public static void finalizarEtapa(final Context context, String ID_ETAPA, final String CODIGO_ETAPA, final String ID_ARCO, final String MEUS_ARCOS) {


//        Call<String> stringCall = ConfigRetrofit.getService().finalizarEtapa(ID_ETAPA, CODIGO_ETAPA);
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.code() == 200) {
//
//                    context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", ID_ARCO).putExtra("MEUS_ARCOS", MEUS_ARCOS));
//                    ((Activity) context).finish();
//
//                } else if (response.code() == 203) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }
}
