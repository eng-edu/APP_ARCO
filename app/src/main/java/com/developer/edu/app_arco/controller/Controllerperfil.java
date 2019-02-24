package com.developer.edu.app_arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
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

    public static void buscarUsuario(final View view, final String ID_USUARIO, final boolean meu_perfil) {

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_perfil);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buscarUsuario(view, ID_USUARIO, meu_perfil);
            }
        });

        ImageView fotoperfil = view.findViewById(R.id.id_perfil_image_perfil);
        final TextView email = view.findViewById(R.id.id_perfil_email);
        final TextView tipo = view.findViewById(R.id.id_perfil_tipo);
        final TextView nome = view.findViewById(R.id.id_perfil_nome);
        final TextView escolaridade = view.findViewById(R.id.id_perfil_escolaridade);
        final TextView bio = view.findViewById(R.id.id_perfil_bio);


        Picasso.get().load(URL_BASE + "/IMG/" + ID_USUARIO + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(fotoperfil);


        Call<String> stringCall = ConfigRetrofit.getService().buscarUsuario(ID_USUARIO);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {

                        JSONObject usuario = new JSONObject(response.body());

                        email.setText(usuario.getString("EMAIL"));

                        if (usuario.getString("TIPO").equals("1")) {
                            tipo.setText("Líder");
                        } else if (usuario.getString("TIPO").equals("2")) {
                            tipo.setText("Menbro");
                        }
                        nome.setText(usuario.getString("NOME") + " " + usuario.getString("SOBRENOME"));
                        escolaridade.setText(usuario.getString("ESCOLARIDADE"));
                        bio.setText(usuario.getString("BIO"));

                        swipeRefreshLayout.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else if (response.code() == 405) {
                    Toast.makeText(view.getContext(), response.body(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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
