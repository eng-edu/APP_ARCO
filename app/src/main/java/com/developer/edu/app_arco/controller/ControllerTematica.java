package com.developer.edu.app_arco.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.developer.edu.app_arco.act.ArcoActivity;
import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Tematica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerTematica {

    static AlertDialog alert;

    public static void bucarTematicas(final Context context, final LayoutInflater inflater) {


        final View view = inflater.inflate(R.layout.list_dados, null);

        final ListView listView = view.findViewById(R.id.list_alert_list);
        final ArrayAdapter<Tematica> arrayAdapter = new ArrayAdapter<Tematica>(context, R.layout.support_simple_spinner_dropdown_item);


        Call<String> stringCall = ConfigRetrofit.getService().buscarTematicas();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    try {
                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        List<Tematica> tematicas = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = array.getJSONObject(i);
                            tematicas.add(new Tematica(object.getString("ID"), object.getString("TITULO"), object.getString("DESCRICAO")));
                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(tematicas);
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
        builder.setTitle("TEMATICAS");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //  Toast.makeText(context, arrayAdapter.getItem(position) + " ", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(context)
                        .setTitle("TEM CERTEZA?")
                        .setMessage("Deseja criar seu Arco com a tematica " + arrayAdapter.getItem(position) + " ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //criar arco
                                alert.dismiss();
                                SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                final String ID = sharedPreferences.getString("ID", "");
                                criarArco(context, arrayAdapter.getItem(position).getId(),ID);

                            }
                        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();

            }
        });


        alert = builder.create();
        alert.show();

    }

    public static void criarArco(final Context context, String idLider, String idTematica) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(true);
        dialog.show();


        Call<String> stringCall = ConfigRetrofit.getService().novoArco(idLider, idTematica);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", response.body()).putExtra("MEUS_ARCOS", "S"));
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
