package com.developer.edu.app_arco.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.app_arco.adapter.AdapterUsuario;
import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEquipe {


    static AlertDialog alert;

       public static void bucarUsuarios(final Context context, final LayoutInflater inflater, final String ID_ARCO, final Socket socket) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();


        final View view = inflater.inflate(R.layout.list_dados, null);

        final ListView listView = view.findViewById(R.id.list_alert_list);
        final AdapterUsuario arrayAdapter = new AdapterUsuario(context, new ArrayList<Usuario>());


        Call<String> stringCall = ConfigRetrofit.getService().buscarUsuarios(ID_ARCO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    try {
                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        List<Usuario> usuarios = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = array.getJSONObject(i);
                            usuarios.add(new Usuario(object.getString("ID"), object.getString("NOME"), object.getString("EMAIL")));
                        }


                        arrayAdapter.clear();
                        arrayAdapter.addAll(usuarios);
                        listView.setAdapter(arrayAdapter);
                        dialog.dismiss();




                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("NOVO MENBRO");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(context)
                        .setTitle("TEM CERTEZA?")
                        .setMessage("Deseja adicionar " + arrayAdapter.getItem(position).getNome() + " a equipe?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                final ProgressDialog dialog = new ProgressDialog(context);
                                dialog.setTitle("Aguarde...");
                                dialog.show();


                                Call<String> stringCall = ConfigRetrofit.getService().novoMenbro(arrayAdapter.getItem(position).getId(), ID_ARCO);
                                stringCall.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        if (response.code() == 200) {
                                            Toast.makeText(context, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                                            socket.emit("EQUIPE",ID_ARCO);
                                            dialog.dismiss();
                                            alert.dismiss();
                                        } else if (response.code() == 405) {
                                            Toast.makeText(context, "O número de membros chegou ao limite!", Toast.LENGTH_SHORT).show();
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

}
