package com.developer.edu.app_arco.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.app_arco.act.EtapaActivity;
import com.developer.edu.app_arco.act.PerfilActivity;
import com.developer.edu.app_arco.adapter.AdapterOpiniao;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Opiniao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ControllerArcosCompartilhados {

    public static void buscarArcos(final Context context, final LayoutInflater inflater, final AdapterOpiniao arrayAdapter, final ListView listView) {

        Call<String> stringCall = ConfigRetrofit.getService().buscarTodosArcos();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        List<Opiniao> opinioes = new ArrayList<>();

                        for (int i = 0; i < size; i++) {

                            JSONObject object = array.getJSONObject(i);
                            opinioes.add(new Opiniao(
                                    object.getString("ID"),
                                    object.getString("ID_ETAPA"),
                                    object.getString("NOME_ETAPA"),
                                    object.getString("ID_USUARIO"),
                                    object.getString("DATA_HORA"),
                                    object.getString("TEXTO"),
                                    object.getString("ID_LIDER")));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(opinioes);
                        listView.setAdapter(arrayAdapter);


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


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    context.startActivity(new Intent(context, PerfilActivity.class).putExtra("ID_USUARIO", arrayAdapter.getItem(position).getID_LIDER()).putExtra("MEU_PERFIL", "N"));
                return false;
            }
        });

    }

}
