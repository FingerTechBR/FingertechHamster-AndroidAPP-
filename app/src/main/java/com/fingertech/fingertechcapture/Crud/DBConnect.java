package com.fingertech.fingertechcapture.Crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fingertech.fingertechcapture.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DBConnect {

    private SQLiteDatabase db;

    String TAG ="db";

    public DBConnect(Context context){
        DBCore dbCore = new DBCore(context);
        db = dbCore.getWritableDatabase();
    }


    public long salvarUsuario(Usuario usuario){


        ContentValues values = new ContentValues();

        values.put("nome", usuario.getNome());
        values.put("endereco", usuario.getEndereco());
        values.put("telefone", usuario.getTelefone());
        values.put("digital_FOTO", usuario.getDigital_caminho());
        values.put("digital", usuario.getDigital());
        values.put("foto", usuario.getFoto());

        return db.insert("usuarios",null,values);

    }


    public List<Usuario> buscaTodos() {
        List<Usuario> users = new ArrayList<Usuario>();

        Cursor cursor = db.rawQuery("select * from usuarios", null);


        if (cursor.moveToFirst())

            do{
                Usuario userretorno = new Usuario();
                userretorno.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id"))));
                userretorno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                userretorno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                userretorno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
                userretorno.setDigital(cursor.getString(cursor.getColumnIndex("digital")));
                userretorno.setDigital_caminho(cursor.getString(cursor.getColumnIndex("digital_FOTO")));
                userretorno.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
                users.add(userretorno);

            }while (cursor.moveToNext());


        db.close();
        cursor.close();
        return users;


    }



}
