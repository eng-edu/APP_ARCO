package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.developer.edu.app_arco.adapter.AdapterEquipe;
import com.developer.edu.app_arco.adapter.AdapterSolicitacao;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Arco;
import com.developer.edu.app_arco.model.Equipe;
import com.developer.edu.app_arco.model.Solicitacao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEquipe {

    public static AlertDialog alert = null;

    public static void buscarEquipe(final Context context, final LayoutInflater inflater, String CODIGO_EQUIPE) {

        Socket socket = SocketStatic.getSocket();

        final View view = inflater.inflate(R.layout.dialog_equipe, null);

        final ListView listView = view.findViewById(R.id.lista_menbros);
        final AdapterEquipe arrayAdapter = new AdapterEquipe(context, new ArrayList<Equipe>(), CODIGO_EQUIPE);

        socket.emit("EQUIPE", CODIGO_EQUIPE);

        socket.on("EQUIPE".concat(CODIGO_EQUIPE), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String result = args[0].toString();
                        arrayAdapter.clear();

                        try {
                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            List<Equipe> solicitacaos = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                JSONObject object = array.getJSONObject(i);

                                solicitacaos.add(new Equipe(
                                        object.getString("ID"),
                                        object.getString("NOME"),
                                        object.getString("SOBRENOME"),
                                        object.getString("DATA_NASC"),
                                        object.getString("ESCOLARIDADE")));
                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(solicitacaos);
                            listView.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Equipe");
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
