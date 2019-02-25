package com.developer.edu.app_arco.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.app_arco.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DB_usuario {

    private SQLiteDatabase bd;
    private DB_OpemHelper opemHelper;

    public DB_usuario(Context context) {
        DB_OpemHelper opemHelper = new DB_OpemHelper(context);
        this.opemHelper = opemHelper;
    }

    public boolean inserir(Usuario u) {

        bd = opemHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", u.getId());
        contentValues.put("nome", u.getNome());
        contentValues.put("sobrenome", u.getSobrenome());
        contentValues.put("escolaridade", u.getEscolaridade());
        contentValues.put("cpf", u.getCpf());
        contentValues.put("data_nasc", u.getData_nasc());
        contentValues.put("email", u.getEmail());
        contentValues.put("sexo", u.getSexo());
        contentValues.put("bio", u.getBio());
        contentValues.put("tipo", u.getTipo());
        contentValues.put("situacao", u.getSituacao());
        contentValues.put("online", u.getOline());

        boolean result = bd.insertWithOnConflict("Usuario", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }

    public boolean atualizar(String id, ContentValues contentValues) {

        bd = opemHelper.getReadableDatabase();
        boolean result = bd.update("Usuario", contentValues, "id = ?", new String[]{id}) > 0;
        bd.close();
        return result;
    }


    public Usuario buscar(String id) {

        Usuario u = new Usuario();

        bd = opemHelper.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM Usuario WHERE id = " + id, null);

        cursor.moveToFirst();
        u.setId(cursor.getString(cursor.getColumnIndex("id")));
        u.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        u.setSobrenome(cursor.getString(cursor.getColumnIndex("sobrenome")));
        u.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
        u.setData_nasc(cursor.getString(cursor.getColumnIndex("data_nasc")));
        u.setEscolaridade(cursor.getString(cursor.getColumnIndex("escolaridade")));
        u.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        u.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
        u.setBio(cursor.getString(cursor.getColumnIndex("bio")));
        u.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
        u.setSituacao(cursor.getString(cursor.getColumnIndex("situacao")));
        u.setOline(cursor.getString(cursor.getColumnIndex("online")));
        cursor.close();

        return u;
    }


    public List<Usuario> listarTodos() {

        bd = opemHelper.getReadableDatabase();

        List<Usuario> usuarios = new ArrayList<>();

        Cursor cursor = bd.rawQuery("SELECT * FROM Usuario", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Usuario u = new Usuario();
                u.setId(cursor.getString(0));
                u.setNome(cursor.getString(1));
                u.setSobrenome(cursor.getString(2));
                u.setCpf(cursor.getString(3));
                u.setData_nasc(cursor.getString(4));
                u.setEmail(cursor.getString(5));
                u.setSexo(cursor.getString(6));
                u.setBio(cursor.getString(7));
                u.setTipo(cursor.getString(8));
                u.setSituacao(cursor.getString(8));
                u.setOline(cursor.getString(10));
                usuarios.add(u);

            } while (cursor.moveToNext());

        }

        cursor.close();

        return usuarios;
    }


    public void deletarTodos() {
        bd = opemHelper.getReadableDatabase();
        bd.delete("Usuario", null, null);
        bd.close();

    }

    public boolean deletar(int id) {
        bd = opemHelper.getReadableDatabase();
        String where = "id = " + id;
        boolean deletSuccessful = bd.delete("Usuario", where, null) > 0;
        bd.close();
        return deletSuccessful;
    }
}
