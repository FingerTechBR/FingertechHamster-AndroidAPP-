package com.fingertech.fingertechcapture.Crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fingertech.fingertechcapture.Models.Usuario;

public class DBConnect {

    private SQLiteDatabase db;



    public DBConnect(Context context){
        DBCore dbCore = new DBCore(context);
        db = dbCore.getWritableDatabase();
    }


    public void salvarUsuario(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("endereco", usuario.getEndereco());
        values.put("telefone", usuario.getTelefone());
        values.put("digital", usuario.getDigital());
        values.put("foto", usuario.getFoto());

        db.insert("usuario",null,values);

    }
}
