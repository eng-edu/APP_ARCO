package com.developer.edu.app_arco.controller;


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEtapa {

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

}
