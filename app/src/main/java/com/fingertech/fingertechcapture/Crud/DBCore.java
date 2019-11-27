package com.fingertech.fingertechcapture.Crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBCore extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "Fingertech";
    private static final String TABELA = "usuarios";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String ENDERECO = "endereco";
    private static final String TELEFONE = "telefone";
    private static final String DIGITAL = "digital";
    private static final String FOTO = "foto";
    private static final int VERSAO = 1;

    public DBCore(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+TABELA+ "(" +
                ID+ "INTEGER PRIMARY KEY AUTOINCReMENT," +
                NOME +"TEXT," +
                TELEFONE+ "TEXT," +
                FOTO+ "TEXT," +
                DIGITAL+ "TEXT" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table "+ TABELA);
        onCreate(sqLiteDatabase);

    }
}
