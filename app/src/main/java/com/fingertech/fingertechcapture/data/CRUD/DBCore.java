package com.fingertech.fingertechcapture.data.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBCore extends SQLiteOpenHelper {


    //tabela 1
    private static final String NOME_BANCO = "Fingertech";
    public static final String TABELA = "usuarios";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String ENDERECO = "endereco";
    public static final String TELEFONE = "telefone";
    public static final String DIGITAL_FOTO = "digital_FOTO";
    public static final String DIGITAL = "digital";
    public static final String FOTO = "foto";
    //fim tabela 1


    //tabela 2
    public static String TABELA2 = "Registro";
    public static String REGISTRADO = "registrado";




    private static final int VERSAO = 3;

    public DBCore(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("create table "+TABELA+ "(" +
                ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME +" TEXT," +
                ENDERECO +" TEXT," +
                TELEFONE+ " TEXT," +
                DIGITAL_FOTO+ " TEXT," +
                DIGITAL+ " TEXT," +
                FOTO+ " TEXT" +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        Log.i("upgrade", "onUpgrade: ");
        sqLiteDatabase.execSQL("drop table "+ TABELA);
        onCreate(sqLiteDatabase);

    }
}
