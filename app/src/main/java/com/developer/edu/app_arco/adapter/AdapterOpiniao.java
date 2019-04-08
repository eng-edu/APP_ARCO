package com.developer.edu.app_arco.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.act.ComentarioActivity;
import com.developer.edu.app_arco.conectionAPI.SocketStatic;
import com.developer.edu.app_arco.controller.ControllerOpiniao;
import com.developer.edu.app_arco.model.Opiniao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class AdapterOpiniao extends ArrayAdapter<Opiniao> {

    private Context context;
    private List<Opiniao> opinioes;
    private String MEUS_ARCOS;
    int curtiu = 2;

    Socket socket = SocketStatic.getSocket();

    public AdapterOpiniao(Context context, List<Opiniao> opinioes, String MEUS_ARCOS) {
        super(context, R.layout.adapter_opiniao, opinioes);
        this.context = context;
        this.opinioes = opinioes;
        this.MEUS_ARCOS = MEUS_ARCOS;
    }

    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_opiniao, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String ID_USUARIO = sharedPreferences.getString("ID", "");

        final TextView data_hora = view.findViewById(R.id.id_adatper_opiniao_data_hora);
        final TextView nome_etapa = view.findViewById(R.id.id_adatper_opiniao_nome_etapa);
        final TextView texto = view.findViewById(R.id.id_adapter_opiniao_texto);
        final TextView qtd_curtida_estrelas = view.findViewById(R.id.id_adatper_opiniao_qtd_curtida_estrelas);
        final TextView id_usuario = view.findViewById(R.id.id_adatper_opiniao_id_usuario);

        final ImageView e1 = view.findViewById(R.id.id_adatper_opiniao_estrela1);
        final ImageView e2 = view.findViewById(R.id.id_adatper_opiniao_estrela2);
        final ImageView e3 = view.findViewById(R.id.id_adatper_opiniao_estrela3);
        final ImageView e4 = view.findViewById(R.id.id_adatper_opiniao_estrela4);
        final ImageView e5 = view.findViewById(R.id.id_adatper_opiniao_estrela5);

        final ImageView curtida = view.findViewById(R.id.id_adatper_opiniao_curtida);
        final ImageView denuncia = view.findViewById(R.id.id_adatper_opiniao_denuncia);
        final ImageView msg = view.findViewById(R.id.id_adatper_opiniao_msg);

        if (MEUS_ARCOS.equals("N")) {
            msg.setVisibility(View.GONE);
        }

        final Opiniao opiniao = opinioes.get(position);


        try {
            JSONObject object = new JSONObject();
            object.put("ID_USUARIO", ID_USUARIO);
            object.put("ID_OPINIAO", opiniao.getID());
            socket.emit("EU_CURTI", object);
            socket.emit("EU_ESTRELAS", object);
            socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        data_hora.setText(opiniao.getDATA_HORA());
        nome_etapa.setText(opiniao.getNOME_ETAPA());
        texto.setText(opiniao.getTEXTO());
        id_usuario.setText("Membro: " + opiniao.getID_USUARIO());


        if (ID_USUARIO.equals(opiniao.getID_USUARIO())) {
            id_usuario.setText("Eu");

        }

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

                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(1, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(1), ID_USUARIO, socket, opiniao.getID_USUARIO());
                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(2, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(2), ID_USUARIO, socket, opiniao.getID_USUARIO());
                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(3, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(3), ID_USUARIO, socket, opiniao.getID_USUARIO());
                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(4, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(4), ID_USUARIO, socket, opiniao.getID_USUARIO());
                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definirIconPontos(5, e1, e2, e3, e4, e5);
                emitEstrelas(opiniao, String.valueOf(5), ID_USUARIO, socket, opiniao.getID_USUARIO());
                socket.emit("QTD_CURTIDAS_ESTRELAS", opiniao.getID());
            }
        });


        socket.on("EU_CURTI".concat(opiniao.getID() + ID_USUARIO), new Emitter.Listener() {
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


                        Socket socket = SocketStatic.getSocket();


                        try {

                            JSONObject object1 = new JSONObject();
                            object1.put("ID_USUARIO", opiniao.getID_USUARIO());
                            object1.put("CODIGO_ETAPA", "1");
                            socket.emit("ESPECIALIDADE", object1);

                            JSONObject object2 = new JSONObject();
                            object2.put("ID_USUARIO", opiniao.getID_USUARIO());
                            object2.put("CODIGO_ETAPA", "2");
                            socket.emit("ESPECIALIDADE", object2);

                            JSONObject object3 = new JSONObject();
                            object3.put("ID_USUARIO", opiniao.getID_USUARIO());
                            object3.put("CODIGO_ETAPA", "3");
                            socket.emit("ESPECIALIDADE", object3);

                            JSONObject object4 = new JSONObject();
                            object4.put("ID_USUARIO", opiniao.getID_USUARIO());
                            object4.put("CODIGO_ETAPA", "4");
                            socket.emit("ESPECIALIDADE", object4);

                            JSONObject object5 = new JSONObject();
                            object5.put("ID_USUARIO", opiniao.getID_USUARIO());
                            object5.put("CODIGO_ETAPA", "5");
                            socket.emit("ESPECIALIDADE", object5);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        socket.on("EU_ESTRELAS".concat(opiniao.getID() + ID_USUARIO), new Emitter.Listener() {
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


        socket.on("QTD_CURTIDAS_ESTRELAS".concat(opiniao.getID()), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString();
                        qtd_curtida_estrelas.setText(result);
                    }
                });
            }
        });

        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDenuncia(context, ID_USUARIO, opiniao.getID());
            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ComentarioActivity.class);
                intent.putExtra("ID_OPINIAO", opiniao.getID());
                context.startActivity(intent);
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


    public static void emitEstrelas(Opiniao opiniao, String quantidade, String ID_USUARIO, Socket socket, String oID_USUARIO) {
        try {
            JSONObject object = new JSONObject();
            object.put("ID_USUARIO", ID_USUARIO);
            object.put("ID_OPINIAO", opiniao.getID());
            object.put("QUANTIDADE", quantidade);
            socket.emit("ESTRELAS", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            JSONObject object1 = new JSONObject();
            object1.put("ID_USUARIO", oID_USUARIO);
            object1.put("CODIGO_ETAPA", "1");
            socket.emit("ESPECIALIDADE", object1);

            JSONObject object2 = new JSONObject();
            object2.put("ID_USUARIO", oID_USUARIO);
            object2.put("CODIGO_ETAPA", "2");
            socket.emit("ESPECIALIDADE", object2);

            JSONObject object3 = new JSONObject();
            object3.put("ID_USUARIO", oID_USUARIO);
            object3.put("CODIGO_ETAPA", "3");
            socket.emit("ESPECIALIDADE", object3);

            JSONObject object4 = new JSONObject();
            object4.put("ID_USUARIO", oID_USUARIO);
            object4.put("CODIGO_ETAPA", "4");
            socket.emit("ESPECIALIDADE", object4);

            JSONObject object5 = new JSONObject();
            object5.put("ID_USUARIO", oID_USUARIO);
            object5.put("CODIGO_ETAPA", "5");
            socket.emit("ESPECIALIDADE", object5);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void enviarDenuncia(final Context context, final String ID_USUARIO, final String ID_OPINIAO) {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setMessage("Descreva o motivo da sua denúncia...");
        // DECLARACAO DO EDITTEXT
        final EditText input = new EditText(context);
        mensagem.setView(input);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        mensagem.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mensagem.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.length() > 0) {
                    ControllerOpiniao.denunciar(context, ID_USUARIO, ID_OPINIAO, input.getText().toString());
                }
            }
        });

        mensagem.show();

    }


}

