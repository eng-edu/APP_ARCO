package com.developer.edu.app_arco.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_OpemHelper extends SQLiteOpenHelper {

    private static final String nomeBD = "DB01";
    private static final int versaoBD = 1;

    private String createTableUsuario =
            "create table Usuario(" +
                    "id integer primary key, " +
                    "nome text, " +
                    "sobrenome text," +
                    "escolaridade text," +
                    "cpf text," +
                    "data_nasc text," +
                    "email text," +
                    "sexo text," +
                    "bio text," +
                    "tipo text," +
                    "situacao text," +
                    "online text" +
                    ");";

    private String createTableEscolaridade =
            "create table Escolaridade(" +
                    "id integer primary key, " +
                    "instituicao text, " +
                    "area text," +
                    "ano text," +
                    "grupos text," +
                    "descricao text," +
                    "id_usuario" +
                    ");";


    private String dropTableUsuario = "drop table Usuario";
    private String dropTableEscolaridade = "drop table Escolaridade";


    public DB_OpemHelper(Context context) {
        super(context, nomeBD, null, versaoBD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL(createTableUsuario);
        bd.execSQL(createTableEscolaridade);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {

        bd.execSQL(dropTableUsuario);
        bd.execSQL(dropTableEscolaridade);
        onCreate(bd);
    }
}
