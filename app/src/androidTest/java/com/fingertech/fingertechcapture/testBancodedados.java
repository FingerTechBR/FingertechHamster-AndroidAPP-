package com.fingertech.fingertechcapture;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.fingertech.fingertechcapture.Models.Usuario;
import com.fingertech.fingertechcapture.data.CRUD.DBConnect;
import com.fingertech.fingertechcapture.Utils.JavaMail.envarEmail;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class testBancodedados  extends TestCase {



/*
    @Test
    public  void testcheckdb(){

        Context context =  InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBConnect dbConnect = new DBConnect(context);
        Usuario user = new Usuario();

        user.setNome("lincoln");
        user.setFoto("db");
        user.setDigital_caminho("db");
        user.setTelefone("32323");
        user.setEndereco("pedro custudio");

        long iduser = dbConnect.salvarUsuario(user);
        boolean resultado = dbConnect.salvarUsuario(user) > 0 ? true : false;
        //boolean result = iduser > 0 ? true : false;





        assertTrue(resultado);
    }


    @Test
    public void testretornatodosusuarios(){

        Context context =  InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBConnect dbConnect = new DBConnect(context);

        List<Usuario> users;
        users = dbConnect.buscaTodos();

        boolean result = users != null ? true : false;


        assertTrue(result);


    }

    @Test
    public void testverificaseestanullnome(){
        Context context =  InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBConnect dbConnect = new DBConnect(context);

        List<Usuario> users;


        users = dbConnect.buscaTodos();

        boolean resultado = true;
       for(Usuario users1 : users){

           if(users1.getNome() == null){
               resultado = false;
           }

       }
       assertTrue(resultado);

    }
*/


    public void testEnviar_email(){
        envarEmail envmail = new envarEmail(InstrumentationRegistry.getInstrumentation().getTargetContext(),"suporte@fingertech.com.br","Jnunit test automatizado de teste unitario", "corpo email");
        //assertTrue(envmail.enviarEmail());

    }




}
