package com.developer.edu.app_arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerOpiniao {

    public static void denunciar(final Context context, String ID_USUARIO, final String ID_OPINIAO, String TEXTO) {


        Call<String> stringCall = ConfigRetrofit.getService().denunciar(ID_OPINIAO, ID_USUARIO, TEXTO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();


                } else if (response.code() == 203) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
              
            }
        });

    }


}
