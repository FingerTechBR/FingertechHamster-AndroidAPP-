package com.fingertech.fingertechcapture;

import androidx.test.platform.app.InstrumentationRegistry;

import com.fingertech.fingertechcapture.Models.Usuario;
import com.fingertech.fingertechcapture.data.CRUD.DBConnect;

import org.junit.Test;


public class testBancodedados {



    @Test
    public  void checkdb(){
        DBConnect dbConnect = new DBConnect(InstrumentationRegistry.getInstrumentation().getContext());
        Usuario user = new Usuario();

        user.setNome("lincoln");
        user.setFoto("db");
        user.setDigital_caminho("db");
        user.setTelefone("32323");
        user.setEndereco("pedro custudio");

        long i = dbConnect.salvarUsuario(user);







    }
}
