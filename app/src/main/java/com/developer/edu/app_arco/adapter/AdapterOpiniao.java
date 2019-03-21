package com.developer.edu.app_arco.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.model.Opiniao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class AdapterOpiniao extends ArrayAdapter<Opiniao> {

    private Context context;
    private List<Opiniao> opinioes;
    int curtiu = 2;

    Socket socket = SocketStatic.getSocket();

    public AdapterOpiniao(Context context, List<Opiniao> opinioes) {
        super(context, R.layout.adapter_opiniao, opinioes);
        this.context = context;
        this.opinioes = opinioes;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_opiniao, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        final TextView data_hora = view.findViewById(R.id.id_adatper_opiniao_data_hora);
        final TextView nome_etapa = view.findViewById(R.id.id_adatper_opiniao_nome_etapa);
        final TextView texto = view.findViewById(R.id.id_adapter_opiniao_texto);

        final ImageView e1 = view.findViewById(R.id.id_adatper_opiniao_estrela1);
        final ImageView e2 = view.findViewById(R.id.id_adatper_opiniao_estrela2);
        final ImageView e3 = view.findViewById(R.id.id_adatper_opiniao_estrela3);
        final ImageView e4 = view.findViewById(R.id.id_adatper_opiniao_estrela4);
        final ImageView e5 = view.findViewById(R.id.id_adatper_opiniao_estrela5);

        final ImageView curtida = view.findViewById(R.id.id_adatper_opiniao_curtida);

        final Opiniao opiniao = opinioes.get(position);


        try {
            JSONObject object = new JSONObject();
            object.put("ID_USUARIO", ID_USUARIO);
            object.put("ID_OPINIAO", opiniao.getID());
            socket.emit("EU_CURTI", object);
            socket.emit("EU_ESTRELAS", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        data_hora.setText(opiniao.getDATA_HORA());
        nome_etapa.setText(opiniao.getNOME_ETAPA());
        texto.setText(opiniao.getTEXTO());

        curtida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curtiu == 1) {
                    curti(1, curtida);
                    emitCurtida(opiniao, String.valueOf(curtiu), ID_USUARIO, socket);
                    curtiu = 2;
                } else {
                    curti(2, curtida);
                    emitCurtida(opiniao, String.valueOf(curtiu), ID_USUARIO, socket);
                    curtiu = 1;
                }

            }
        });


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(1, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(1), ID_USUARIO, socket);
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(2, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(2),ID_USUARIO, socket);
            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(3, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(3),ID_USUARIO, socket);
            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(4, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(4), ID_USUARIO, socket);
            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(5, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(5), ID_USUARIO, socket);
            }
        });


        socket.on("EU_CURTI".concat(ID_USUARIO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        int i = Integer.parseInt(result);

                        if (i == 1) {
                            curti(1, curtida);
                            curtiu = 2;
                        } else {
                            curti(2, curtida);
                            curtiu = 1;
                        }

                    }
                });
            }
        });


        socket.on("EU_ESTRELAS".concat(ID_USUARIO), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        definirIconPontos(Integer.parseInt(result), e1, e2, e3, e4, e5);
                    }
                });
            }
        });


        return view;
    }


    public static void definirIconPontos(int ponto, ImageView estrela1, ImageView
            estrela2, ImageView estrela3, ImageView estrela4, ImageView estrela5) {

        if (ponto == 1) {
            estrela1.setImageResource(R.mipmap.ic_star2);
            estrela2.setImageResource(R.mipmap.ic_star1);
            estrela3.setImageResource(R.mipmap.ic_star1);
            estrela4.setImageResource(R.mipmap.ic_star1);
            estrela5.setImageResource(R.mipmap.ic_star1);

        } else if (ponto == 2) {
            estrela1.setImageResource(R.mipmap.ic_star2);
            estrela2.setImageResource(R.mipmap.ic_star2);
            estrela3.setImageResource(R.mipmap.ic_star1);
            estrela4.setImageResource(R.mipmap.ic_star1);
            estrela5.setImageResource(R.mipmap.ic_star1);

        } else if (ponto == 3) {
            estrela1.setImageResource(R.mipmap.ic_star2);
            estrela2.setImageResource(R.mipmap.ic_star2);
            estrela3.setImageResource(R.mipmap.ic_star2);
            estrela4.setImageResource(R.mipmap.ic_star1);
            estrela5.setImageResource(R.mipmap.ic_star1);

        } else if (ponto == 4) {
            estrela1.setImageResource(R.mipmap.ic_star2);
            estrela2.setImageResource(R.mipmap.ic_star2);
            estrela3.setImageResource(R.mipmap.ic_star2);
            estrela4.setImageResource(R.mipmap.ic_star2);
            estrela5.setImageResource(R.mipmap.ic_star1);

        } else if (ponto == 5) {
            estrela1.setImageResource(R.mipmap.ic_star2);
            estrela2.setImageResource(R.mipmap.ic_star2);
            estrela3.setImageResource(R.mipmap.ic_star2);
            estrela4.setImageResource(R.mipmap.ic_star2);
            estrela5.setImageResource(R.mipmap.ic_star2);

        }

    }

    public static void curti(int curti, ImageView curtida) {

        if (curti == 1) {
            curtida.setImageResource(R.mipmap.ic_gostei);
        } else if (curti == 2) {
            curtida.setImageResource(R.mipmap.ic_ngostei);

        }
    }

    public static void emitCurtida(Opiniao opiniao, String curti, String id_usuario, Socket socket) {


        try {
            JSONObject object = new JSONObject();
            object.put("ID_USUARIO", id_usuario);
            object.put("ID_OPINIAO", opiniao.getID());
            object.put("CURTIU", curti);
            socket.emit("CURTIU", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static void emitEstrelas(Opiniao opiniao, String quantidade, String id_usuario, Socket socket) {
        try {
            JSONObject object = new JSONObject();
            object.put("ID_USUARIO", id_usuario);
            object.put("ID_OPINIAO", opiniao.getID());
            object.put("QUANTIDADE", quantidade);
            socket.emit("ESTRELAS", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

