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
    private String dropTableUsuario = "drop table Usuario";


    public DB_OpemHelper(Context context) {
        super(context, nomeBD, null, versaoBD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL(createTableUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {

        bd.execSQL(dropTableUsuario);
        onCreate(bd);
    }
}
