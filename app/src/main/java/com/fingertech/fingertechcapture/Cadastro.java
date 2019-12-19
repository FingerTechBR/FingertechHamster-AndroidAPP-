package com.fingertech.fingertechcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.data.CRUD.DBConnect;
import com.fingertech.fingertechcapture.interfaces.permissoes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cadastro extends AppCompatActivity implements permissoes {


    private DBConnect dbConnect;


    @BindView(R.id.login_btn_entrar)
    Button login_btn_entrar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void initConfig() {

        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(this);
        ButterKnife.bind(this,this);

        dbConnect = new DBConnect(this);


    }




    @Override
    public String[] permissoesnecessarias() {
        String[] APPPERMISSOES = {

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET
        };
        return APPPERMISSOES;

    }


    @OnClick(R.id.login_btn_entrar)
    public void lgon_btn_entrar(){

        startActivity(new Intent(this, MainActivity.class));

    }
}
