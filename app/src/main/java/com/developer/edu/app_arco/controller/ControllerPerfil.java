package com.developer.edu.app_arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.PerfilActivity;
import com.developer.edu.app_arco.bd.DB_usuario;
import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.model.Usuario;
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

public class ControllerPerfil {

    public static void buscarPerfil(final View view, final String ID_USUARIO, final boolean meu_perfil, final SwipeRefreshLayout swipeRefreshLayout) {

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

                        Usuario objusuario = new Usuario();
                        JSONObject usuario = new JSONObject(response.body());

                        objusuario.setId(usuario.getString("ID"));
                        objusuario.setNome(usuario.getString("NOME"));
                        objusuario.setSobrenome(usuario.getString("SOBRENOME"));
                        objusuario.setEscolaridade(usuario.getString("ESCOLARIDADE"));
                        objusuario.setCpf(usuario.getString("CPF"));
                        objusuario.setData_nasc(usuario.getString("DATA_NASC"));
                        objusuario.setEmail(usuario.getString("EMAIL"));
                        objusuario.setSexo(usuario.getString("SEXO"));
                        objusuario.setBio(usuario.getString("BIO"));
                        objusuario.setTipo(usuario.getString("TIPO"));
                        objusuario.setSituacao(usuario.getString("SITUACAO"));
                        objusuario.setOline(usuario.getString("ONLINE"));

                        DB_usuario db_usuario = new DB_usuario(view.getContext());
                        db_usuario.deletar(Integer.parseInt(ID_USUARIO));
                        db_usuario.inserir(objusuario);


                        email.setText(objusuario.getEmail());

                        if (objusuario.getTipo().equals("1")) {
                            tipo.setText("Líder");
                        } else if (objusuario.getTipo().equals("2")) {
                            tipo.setText("Menbro");
                        }
                        nome.setText(objusuario.getNome() + " " + objusuario.getSobrenome());
                        escolaridade.setText(objusuario.getEscolaridade());
                        bio.setText(objusuario.getBio());

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

    public static void alterarPerfil(final Context context,
                                     final String id,
                                     final String meuperfil,
                                     String pathfoto,
                                     String bio,
                                     String nome,
                                     String sobrenome,
                                     String cpf,
                                     String sexo,
                                     String data_nasc,
                                     String escolaridade,
                                     String tipo) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        if(tipo.equals("2")){
            cpf = " - ";
        }
        Call<String> stringCall = null;

        if (pathfoto == null || pathfoto.equals("")) {

            stringCall = ConfigRetrofit.getService().alterarUsuario(id, bio, nome, sobrenome, cpf, sexo, data_nasc, escolaridade);

        } else {
            File file = new File(pathfoto);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            stringCall = ConfigRetrofit.getService().alterarComFotoUsuario(id, bio, nome, sobrenome, cpf, sexo, data_nasc, escolaridade, fileToUpload, filename);
        }


        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Intent intent = new Intent(context, PerfilActivity.class);
                    intent.putExtra("MEU_PERFIL", meuperfil);
                    intent.putExtra("ID_USUARIO", id);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                    dialog.dismiss();
                } else {
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
