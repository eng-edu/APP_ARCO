package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerCadastro {

    public static void cadastrar(final Context context, String nome, String idade, String sexo, String escolaridade, final String email, final String senha, String img, String tipo) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        File file = new File(img);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        Call<String> stringCall = ConfigRetrofit.getService().inserirUsuario(nome, idade, sexo, escolaridade, email, senha, tipo, fileToUpload, filename);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    //chama o controller login
                    ControllerLogin.logar(context, email, senha);
                    ((Activity) context).finish();
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
