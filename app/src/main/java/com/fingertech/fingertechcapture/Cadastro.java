package com.fingertech.fingertechcapture;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.fingertechcapture.Utils.JavaMail.envarEmail;
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



    @BindView(R.id.login_txt_nome)
    EditText login_txt_nome;
    @BindView(R.id.login_txt_email)
    EditText login_txt_email;


    private SharedPreferences sharedPreference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initConfig();
        if(sharedPreference.getBoolean("cadastrado", false)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    @Override
    protected void onPause() {

        super.onPause();
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initConfig() {



        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(this);
        ButterKnife.bind(this,this);
        dbConnect = new DBConnect(this);
        sharedPreference = this.getSharedPreferences("Fingertech",Context.MODE_PRIVATE);

    }
    @OnClick(R.id.login_btn_entrar)
    public void lgon_btn_entrar(){


        Context context = this;

        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean("cadastrado", true);
        editor.commit();
        String corpoemail = " Nome: "+ login_txt_nome.getText()+"\n "
                          + " Email: "+ login_txt_email.getText();
        envarEmail envmail = new envarEmail(getApplicationContext(), this,"suporte@fingertech.com.br","Aplicativo Fingertech", corpoemail);
        envmail.execute();



    }

    @Override
    public String[] permissoesnecessarias() {
        String[] APPPERMISSOES = {

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,

        };
        return APPPERMISSOES;

    }



}
