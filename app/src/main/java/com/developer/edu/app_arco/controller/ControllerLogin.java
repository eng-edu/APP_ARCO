package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.developer.edu.app_arco.MenuActivity;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ControllerLogin {

    public static void logar(final Context context, final String email, final String senha) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().buscarUsuarioEmailSenha(email,senha);
                stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject usuario = new JSONObject(response.body());
                        final SharedPreferences.Editor editor = context.getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
                        editor.putString("ID", usuario.getString("ID"));
                        editor.putString("TIPO", usuario.getString("TIPO"));
                        editor.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //chama menu principal
                    context.startActivity(new Intent(context, MenuActivity.class));
                    ((Activity) context).finish();
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

}
