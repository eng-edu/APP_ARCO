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

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.ArcoActivity;
import com.developer.edu.app_arco.adapter.AdapterArco;
import com.developer.edu.app_arco.adapter.AdapterRanking;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Arco;
import com.developer.edu.app_arco.model.Ranking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArco {


    static AlertDialog alert;

    public static void novoArco(final Context context, final String CODIGO_EQUIPE, final String ID_USUARIO) {

        final Socket socket = SocketStatic.getSocket();

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().novoArcoMenbro(CODIGO_EQUIPE, ID_USUARIO);

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {


                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        socket.emit("NUM_NOTIFICACAO", object.getString("ID_USUARIO"));
                        socket.emit("NOTIFICACAO", object.getString("ID_USUARIO"));
                        socket.emit("SOLICITACAO", CODIGO_EQUIPE);
                        socket.emit("NUM_SOLICITACAO", CODIGO_EQUIPE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }


                    AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
                    mensagem.setMessage("Aguarde a aprovação do líder!");
                    mensagem.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    mensagem.show();

                    dialog.dismiss();
                } else if (response.code() == 203) {
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

    public static void bucarMeusArco(final Context context, final LayoutInflater inflater) {


        final View view = inflater.inflate(R.layout.dialog_arco, null);

        final ListView listView = view.findViewById(R.id.lista_arcos);
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
                                    object.getString("SITUACAO"),
                                    object.getString("ID_LIDER"),
                                    object.getString("NOME_TEMATICA"),
                                    object.getString("DESCRICAO_TEMATICA"),
                                    object.getString("DATA_HORA")));

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
        builder.setMessage("Meus arcos");
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
                context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", arrayAdapter.getItem(position).getID()).putExtra("MEUS_ARCOS", "S"));
                ((Activity) context).finish();
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


        Call<String> stringCall = ConfigRetrofit.getService().novoArcoLider(idLider, idTematica);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", object.optString("ID_ARCO")).putExtra("MEUS_ARCOS", "S"));
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                } else if (response.code() == 203) {
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

    public static void bucarRanking(final Context context, final LayoutInflater inflater) {


        final View view = inflater.inflate(R.layout.dialog_arco, null);

        final ListView listView = view.findViewById(R.id.lista_arcos);
        final AdapterRanking arrayAdapter = new AdapterRanking(context, new ArrayList<Ranking>());

        Call<String> stringCall = ConfigRetrofit.getService().buscarRanking();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    try {
                        JSONArray array = new JSONArray(response.body());
                        int size = array.length();
                        List<Ranking> rankings = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = array.getJSONObject(i);

                            rankings.add(new Ranking(
                                    object.getString("ID_USUARIO"),
                                    object.getString("CURTIDAS"),
                                    object.getString("ESTRELAS")));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(rankings);
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
        builder.setMessage("Ranking");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert = builder.create();
        alert.show();

    }


}
