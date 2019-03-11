package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.adapter.AdapterNotificacao;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Notificacao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ControllerNotificacao {


    public static AlertDialog alert = null;

    public static void buscarNoticacoes(final Context context, final LayoutInflater inflater, String ID_USUARIO) {

        Socket socket = SocketStatic.getSocket();

        final View view = inflater.inflate(R.layout.dialog_notificacao, null);

        final ListView listView = view.findViewById(R.id.lista_notificacoes);
        final AdapterNotificacao arrayAdapter = new AdapterNotificacao(context, new ArrayList<Notificacao>());

        socket.emit("NOTIFICACAO", ID_USUARIO);

        socket.on("NOTIFICACAO".concat(ID_USUARIO), new Emitter.Listener() {
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
                            List<Notificacao> notificacaos = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                JSONObject object = array.getJSONObject(i);

                                notificacaos.add(new Notificacao(
                                        object.getString("ID"),
                                        object.getString("ID_ARCO"),
                                        object.getString("ID_USUARIO"),
                                        object.getString("DATA_HORA"),
                                        object.getString("TEXTO")));
                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(notificacaos);
                            listView.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Notificações");
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

