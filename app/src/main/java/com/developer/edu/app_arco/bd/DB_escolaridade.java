package com.developer.edu.app_arco.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.app_arco.model.Escolaridade;

import java.util.ArrayList;
import java.util.List;

public class DB_escolaridade {

    private SQLiteDatabase bd;
    private DB_OpemHelper opemHelper;

    public DB_escolaridade(Context context) {
        DB_OpemHelper opemHelper = new DB_OpemHelper(context);
        this.opemHelper = opemHelper;
    }

    public boolean inserir(Escolaridade f) {

        bd = opemHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", f.getId());
        contentValues.put("instituicao", f.getInstituicao());
        contentValues.put("area", f.getArea());
        contentValues.put("ano", f.getAno());
        contentValues.put("grupos", f.getGrupos());
        contentValues.put("descricao", f.getDescricao());
        contentValues.put("id_usuario", f.getId_usuario());

        boolean result = bd.insertWithOnConflict("Escolaridade", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }

    public boolean atualizar(String id, ContentValues contentValues) {

        bd = opemHelper.getReadableDatabase();
        boolean result = bd.update("Escolaridade", contentValues, "id = ?", new String[]{id}) > 0;
        bd.close();
        return result;
    }


    public Escolaridade buscar(String id) {

        Escolaridade f = new Escolaridade();

        bd = opemHelper.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM Formacao WHERE id = " + id, null);

        cursor.moveToFirst();

        f.setId(cursor.getString(cursor.getColumnIndex("id")));
        f.setInstituicao(cursor.getString(cursor.getColumnIndex("instituicao")));
        f.setArea(cursor.getString(cursor.getColumnIndex("area")));
        f.setAno(cursor.getString(cursor.getColumnIndex("ano")));
        f.setGrupos(cursor.getString(cursor.getColumnIndex("grupos")));
        f.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        f.setId_usuario(cursor.getString(cursor.getColumnIndex("id_usuario")));

        cursor.close();

        return f;
    }


    public List<Escolaridade> listarTodos() {

        bd = opemHelper.getReadableDatabase();

        List<Escolaridade> formacoes = new ArrayList<>();

        Cursor cursor = bd.rawQuery("SELECT * FROM Formacao", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Escolaridade f = new Escolaridade();
                f.setId(cursor.getString(cursor.getColumnIndex("id")));
                f.setInstituicao(cursor.getString(cursor.getColumnIndex("instituicao")));
                f.setArea(cursor.getString(cursor.getColumnIndex("area")));
                f.setAno(cursor.getString(cursor.getColumnIndex("ano")));
                f.setGrupos(cursor.getString(cursor.getColumnIndex("grupos")));
                f.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                f.setId_usuario(cursor.getString(cursor.getColumnIndex("id_usuario")));

                formacoes.add(f);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return formacoes;
    }


    public void deletarTodos() {
        bd = opemHelper.getReadableDatabase();
        bd.delete("Escolaridade", null, null);
        bd.close();

    }

    public boolean deletar(int id) {
        bd = opemHelper.getReadableDatabase();
        String where = "id = " + id;
        boolean deletSuccessful = bd.delete("Escolaridade", where, null) > 0;
        bd.close();
        return deletSuccessful;
    }
}
