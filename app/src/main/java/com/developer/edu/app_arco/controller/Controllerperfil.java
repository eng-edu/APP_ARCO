package com.developer.edu.app_arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class Controllerperfil {

    public static void buscarUsuario(final Context context, final TextView pontos, final ImageView fotoperfil, final EditText nome, final EditText idade, final RadioButton sexoM, final RadioButton sexoF, final EditText escolaridade, final EditText email) {


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(true);
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString("ID", "");

        Picasso.get().load(URL_BASE + "/IMG/" + ID + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(fotoperfil);

        Call<String> stringCall = ConfigRetrofit.getService().buscarUsuario(ID);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject usuario = new JSONObject(response.body());
                        pontos.setText(usuario.getString("PONTO") + " pontos");
                        nome.setText(usuario.getString("NOME"));
                        idade.setText(usuario.getString("IDADE"));

                        if (usuario.getString("SEXO").equals("M")) {
                            sexoM.setChecked(true);
                        } else if (usuario.getString("SEXO").equals("F")) {
                            sexoF.setChecked(true);
                        }
                        escolaridade.setText(usuario.getString("ESCOLARIDADE"));
                        email.setText(usuario.getString("EMAIL"));

                        dialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
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


    }


    public static void alterarUsuario(final Context context, EditText nome, EditText idade, String sexo, String pathfoto, EditText escolaridade, final ImageView fotoPerfil) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();
        Call<String> stringCall = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString("ID", "");


        if (pathfoto.equals("") || pathfoto == null) {
            stringCall = ConfigRetrofit.getService().alterarUsuario(ID, nome.getText().toString(), idade.getText().toString(), sexo, escolaridade.getText().toString());
        } else {
            File file = new File(pathfoto);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            stringCall = ConfigRetrofit.getService().alterarComFotoUsuario(ID, nome.getText().toString(), idade.getText().toString(), sexo, escolaridade.getText().toString(), fileToUpload, filename);

        }


        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Picasso.get().load(URL_BASE + "/IMG/" + ID + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(fotoPerfil);
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
