package com.fingertech.fingertechcapture.data.CRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

        values.put(DBCore.NOME, usuario.getNome());
        values.put(DBCore.ENDERECO, usuario.getEndereco());
        values.put(DBCore.TELEFONE, usuario.getTelefone());
        values.put(DBCore.DIGITAL_FOTO, usuario.getDigital_caminho());
        values.put(DBCore.DIGITAL, usuario.getDigital());
        values.put(DBCore.FOTO, usuario.getFoto());

        return db.insert(DBCore.TABELA,null,values);

    }

    public long salvarRegistro(boolean registro){

        ContentValues values = new ContentValues();
        values.put(DBCore.REGISTRADO, registro);
        return db.insert(DBCore.TABELA2, null, values);

    }


    public boolean checkRegistro(){

        boolean resultado;
        Cursor cursor = db.rawQuery("select "+ DBCore.REGISTRADO, null);
        return cursor.moveToFirst();

    }


    public List<Usuario> buscaTodos() {
        List<Usuario> users = new ArrayList<Usuario>();

        Cursor cursor = db.rawQuery("select * from usuarios", null);


        if (cursor.moveToFirst())

            do{
                Usuario userretorno = new Usuario();
                userretorno.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBCore.ID))));
                userretorno.setNome(cursor.getString(cursor.getColumnIndex(DBCore.NOME)));
                userretorno.setEndereco(cursor.getString(cursor.getColumnIndex(DBCore.ENDERECO)));
                userretorno.setTelefone(cursor.getString(cursor.getColumnIndex(DBCore.TELEFONE)));
                userretorno.setDigital(cursor.getString(cursor.getColumnIndex(DBCore.DIGITAL)));
                userretorno.setDigital_caminho(cursor.getString(cursor.getColumnIndex(DBCore.DIGITAL_FOTO)));
                userretorno.setFoto(cursor.getString(cursor.getColumnIndex(DBCore.FOTO)));
                users.add(userretorno);

            }while (cursor.moveToNext());
        db.close();
        cursor.close();
        return users;


    }



}
