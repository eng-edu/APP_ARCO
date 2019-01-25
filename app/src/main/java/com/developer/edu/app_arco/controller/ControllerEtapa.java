package com.developer.edu.app_arco.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.app_arco.EtapaActivity;
import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Etapa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEtapa {

    public static void bucarEtapas(final Context context, final LayoutInflater inflater, String ID_ARCO) {

        final AlertDialog alert;
        final View view = inflater.inflate(R.layout.list_dados, null);
        final ListView listView = view.findViewById(R.id.list_alert_list);
        final ArrayAdapter<Etapa> arrayAdapter = new ArrayAdapter<Etapa>(context, R.layout.support_simple_spinner_dropdown_item);

        Call<String> stringCall = ConfigRetrofit.getService().buscarEtapas(ID_ARCO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    try {

                        JSONArray array = new JSONArray(response.body());
                        List<Etapa> etapaList = new ArrayList<>();

                        int size = array.length();
                        for (int i = 0; i < size; i++) {

                            JSONObject object = array.getJSONObject(i);

                            String codigo = object.getString("CODIGO");
                            String situacao = object.getString("SITUACAO");
                            etapaList.add(new Etapa(
                                    object.getString("ID"),
                                    object.getString("CODIGO"),
                                    object.getString("TITULO"),
                                    object.getString("ID_ARCO"),
                                    object.getString("TEXTO"),
                                    object.getString("PONTO"),
                                    object.getString("SITUACAO")));
                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(etapaList);
                        listView.setAdapter(arrayAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (response.code() == 405) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ETAPAS");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                context.startActivity(new Intent(context, EtapaActivity.class).putExtra("ID_ETAPA", arrayAdapter.getItem(position).getId()));
            }
        });

        alert = builder.create();
        alert.show();
    }
}
