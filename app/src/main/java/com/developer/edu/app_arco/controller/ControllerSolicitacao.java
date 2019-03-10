package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterSolicitacao;
import com.developer.edu.app_arco.model.Solicitacao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ControllerSolicitacao {


    public static AlertDialog alert = null;

    public static void buscarSolicitacoes(final Context context, final LayoutInflater inflater, String CODIGO_EQUIPE, Socket socket) {

        final View view = inflater.inflate(R.layout.dialog_solicitacao, null);

        final ListView listView = view.findViewById(R.id.lista_solicitacoes);
        final AdapterSolicitacao arrayAdapter = new AdapterSolicitacao(context, new ArrayList<Solicitacao>());

        socket.emit("SOLICITACAO", CODIGO_EQUIPE);

        socket.on("SOLICITACAO".concat(CODIGO_EQUIPE), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String result = args[0].toString();

                        try {
                            JSONArray array = new JSONArray(result);
                            int size = array.length();
                            List<Solicitacao> solicitacaos = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                JSONObject object = array.getJSONObject(i);

                                solicitacaos.add(new Solicitacao(
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
        builder.setMessage("Solicitações");
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
