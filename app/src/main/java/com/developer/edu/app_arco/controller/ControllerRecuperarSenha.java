package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerRecuperarSenha {

    public static void recuperarSenha(final Context context, String email, String datanasc) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().recuperarSenha(email, datanasc);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    ((Activity) context).finish();
                    dialog.dismiss();
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
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
