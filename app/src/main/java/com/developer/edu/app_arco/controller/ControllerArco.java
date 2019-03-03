package com.developer.edu.app_arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArco {

//    static AlertDialog alert;
//
//
//    public static void bucarMeusArco2(final Context context, final LayoutInflater inflater) {
//
//
//        final View view = inflater.inflate(R.layout.list_dados2, null);
//        final List<Arco> arcos = new ArrayList<>();
//        final ListView listView = view.findViewById(R.id.list_alert_list2);
//        final AdapterArco2 arrayAdapter = new AdapterArco2(context, new ArrayList<Arco>());
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
//        final String ID_USUARIO = sharedPreferences.getString("ID", "");
//
//        Call<String> stringCall = ConfigRetrofit.getService().buscarMeusArcos(ID_USUARIO);
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.code() == 200) {
//
//                    try {
//                        JSONArray array = new JSONArray(response.body());
//                        int size = array.length();
//
//                        for (int i = 0; i < size; i++) {
//                            JSONObject object = array.getJSONObject(i);
//
//                            arcos.add(new Arco(
//                                    object.getString("ID"),
//                                    object.getString("TEMATICA"),
//                                    object.getString("TITULO"),
//                                    object.getString("PONTO"),
//                                    object.getString("GOSTEI")
//                            ));
//
//                        }
//
//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(arcos);
//                        listView.setAdapter(arrayAdapter);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (response.code() == 405) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("MEUS ARCOS");
//        builder.setView(view);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MediaActivity.clearList();
//                MediaActivity.clearListArcos();
//                for (Arco a : arcos) {
//
//                    if (a.getCheck()) {
//                        MediaActivity.addList(a.getID());
//                        MediaActivity.addArcos(a);
//                    }
//                }
//
//                alert.dismiss();
//                context.startActivity(new Intent(context, MediaActivity.class));
//            }
//        });
//
//        alert = builder.create();
//        alert.show();
//
//    }
//
//    public static void bucarMeusArco(final Context context, final LayoutInflater inflater) {
//
//
//        final View view = inflater.inflate(R.layout.list_dados, null);
//
//        final ListView listView = view.findViewById(R.id.list_alert_list);
//        final AdapterArco arrayAdapter = new AdapterArco(context, new ArrayList<Arco>());
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
//        final String ID_USUARIO = sharedPreferences.getString("ID", "");
//
//        Call<String> stringCall = ConfigRetrofit.getService().buscarMeusArcos(ID_USUARIO);
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.code() == 200) {
//
//                    try {
//                        JSONArray array = new JSONArray(response.body());
//                        int size = array.length();
//                        List<Arco> arcos = new ArrayList<>();
//                        for (int i = 0; i < size; i++) {
//                            JSONObject object = array.getJSONObject(i);
//
//                            arcos.add(new Arco(
//                                    object.getString("ID"),
//                                    object.getString("TEMATICA"),
//                                    object.getString("TITULO"),
//                                    object.getString("PONTO"),
//                                    object.getString("GOSTEI")
//                                    ));
//
//                        }
//
//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(arcos);
//                        listView.setAdapter(arrayAdapter);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (response.code() == 405) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("MEUS ARCOS");
//        builder.setView(view);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                listView.setItemChecked(position, true);
//
//                alert.dismiss();
//                context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", arrayAdapter.getItem(position).getID()).putExtra("MEUS_ARCOS","S"));
//                ((Activity) context).finish();
//            }
//        });
//
//        alert = builder.create();
//        alert.show();
//
//    }
//
//    public static void denunciarArco(final Context context, final String id_usuario, final String id_arco, final String descricao) {
//
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setTitle("Aguarde...");
//        dialog.show();
//
//        Call<String> stringCall = ConfigRetrofit.getService().denunciarArco(id_usuario, id_arco, descricao);
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.code() == 200) {
//
//                    Toast.makeText(context, "Enviado com sucesso!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//
//                } else if (response.code() == 405) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//    }
//
//    public static void bucarArcosCompartilhados(final Context context, final LayoutInflater inflater) {
//
//
//        final View view = inflater.inflate(R.layout.list_dados, null);
//
//        final ListView listView = view.findViewById(R.id.list_alert_list);
//        final AdapterArco arrayAdapter = new AdapterArco(context, new ArrayList<Arco>());
//
//        Call<String> stringCall = ConfigRetrofit.getService().buscarArcosComaprtilhados();
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.code() == 200) {
//
//                    try {
//                        JSONArray array = new JSONArray(response.body());
//                        int size = array.length();
//                        List<Arco> arcos = new ArrayList<>();
//                        for (int i = 0; i < size; i++) {
//                            JSONObject object = array.getJSONObject(i);
//
//                            arcos.add(new Arco(
//                                    object.getString("ID"),
//                                    object.getString("TEMATICA"),
//                                    object.getString("TITULO"),
//                                    object.getString("PONTO"),
//                                    object.getString("GOSTEI")
//                            ));
//
//                        }
//
//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(arcos);
//                        listView.setAdapter(arrayAdapter);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (response.code() == 405) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("ARCOS COMPARTILHADOS");
//        builder.setView(view);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                alert.dismiss();
//                context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", arrayAdapter.getItem(position).getID()).putExtra("MEUS_ARCOS","N"));
//                ((Activity) context).finish();
//            }
//        });
//
//        alert = builder.create();
//        alert.show();
//
//    }
//
//    public static void bucarRanking(final Context context, final LayoutInflater inflater) {
//
//        final ProgressDialog dialog = new ProgressDialog(context);
//        dialog.setTitle("Aguarde...");
//        dialog.show();
//
//        final View view = inflater.inflate(R.layout.list_dados, null);
//
//        final ListView listView = view.findViewById(R.id.list_alert_list);
//        final AdapterUsuario arrayAdapter = new AdapterUsuario(context, new ArrayList<Usuario>());
//
//
//        Call<String> stringCall = ConfigRetrofit.getService().buscarRanking();
//        stringCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.code() == 200) {
//
//                    try {
//                        JSONArray array = new JSONArray(response.body());
//                        int size = array.length();
//                        List<Usuario> usuarios = new ArrayList<>();
//                        for (int i = 0; i < size; i++) {
//                            JSONObject object = array.getJSONObject(i);
//                            usuarios.add(new Usuario(object.getString("ID"), "PONTUAÇÃO: "+object.getString("PONTO"), object.getString("EMAIL")));
//                        }
//
//                        arrayAdapter.clear();
//                        arrayAdapter.addAll(usuarios);
//                        listView.setAdapter(arrayAdapter);
//                        dialog.dismiss();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        dialog.dismiss();
//                    }
//
//                } else if (response.code() == 405) {
//                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("RANKING");
//        builder.setView(view);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                Intent intent = new Intent(context, PerfilActivity.class);
//                intent.putExtra("ID_USUARIO", arrayAdapter.getItem(position).getId());
//                context.startActivity(intent);
//            }
//        });
//
//        alert = builder.create();
//        alert.show();
//
//    }

    public static void criarArco(final Context context, String idLider, String idTematica) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(true);
        dialog.show();


        Call<String> stringCall = ConfigRetrofit.getService().novoArco(idLider, idTematica);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

//                    context.startActivity(new Intent(context, ArcoActivity.class).putExtra("ID_ARCO", response.body()).putExtra("MEUS_ARCOS", "S"));
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
