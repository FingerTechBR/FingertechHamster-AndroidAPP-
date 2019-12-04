package com.fingertech.fingertechcapture.Crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fingertech.fingertechcapture.Models.Usuario;

public class DBConnect {

    private SQLiteDatabase db;



    public DBConnect(Context context){
        DBCore dbCore = new DBCore(context);
        db = dbCore.getWritableDatabase();
    }


    public long salvarUsuario(Usuario usuario){

        String TAG ="db";
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("endereco", usuario.getEndereco());
        values.put("telefone", usuario.getTelefone());
        values.put("digital_FOTO", usuario.getDigital_caminho());
        values.put("digital", usuario.getDigital());
        values.put("foto", usuario.getFoto());

        return db.insert("usuarios",null,values);

    }



}
