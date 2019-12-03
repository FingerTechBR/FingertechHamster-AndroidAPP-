package com.fingertech.fingertechcapture.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fingertech.fingertechcapture.interfaces.permissoes;

import java.util.ArrayList;
import java.util.List;

public class solicita_permissao  {

    String[] perm;


    public static final int codigoPermissao = 1;

    public solicita_permissao(permissoes permissoes){

        this.perm = permissoes.permissoesnecessarias();

    }

    public boolean solicitarpermissao(Activity ac){

        List<String> permissoes = new ArrayList<>();

        for(String per : perm){
            if(ContextCompat.checkSelfPermission(ac,per) != PackageManager.PERMISSION_GRANTED){
                permissoes.add(per);
            }
        }
        if(!permissoes.isEmpty()){

            ActivityCompat.requestPermissions(ac,permissoes.toArray(new String[permissoes.size()]),codigoPermissao);

            return false;
        }
        return true;
    }


}
