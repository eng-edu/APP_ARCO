package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.app_arco.AdapterArco;
import com.developer.edu.app_arco.ArcoActivity;
import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Arco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArco {

    static AlertDialog alert;

    public static void bucarMeusArco(final Context context, final LayoutInflater inflater) {


        final View view = inflater.inflate(R.layout.list_dados, null);

        final ListView listView = view.findViewById(R.id.list_alert_list);
        final AdapterArco arrayAdapter = new AdapterArco(context, new ArrayList<Arco>());

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        Call<String> stringCall = ConfigRetrofit.getService().buscarMeusArcos(ID_USUARIO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    try {
                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        List<Arco> arcos = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = array.getJSONObject(i);

                            arcos.add(new Arco(
                                    object.getString("ID"),
                                    object.getString("TEMATICA"),
                                    object.getString("TITULO"),
                                    object.getString("PONTO"),
                                    object.getString("GOSTEI")
                                    ));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(arcos);
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
        builder.setTitle("MEUS ARCOS");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                alert.dismiss();
                context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", arrayAdapter.getItem(position).getID()).putExtra("MEUS_ARCO","S"));
                ((Activity) context).finish();
            }
        });

        alert = builder.create();
        alert.show();

    }

    public static void denunciarArco(final Context context, final String id_usuario, final String id_arco, final String descricao) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().denunciarArco(id_usuario, id_arco, descricao);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Toast.makeText(context, "Enviado com sucesso!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                } else if (response.code() == 405) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }


}
