package com.fingertech.fingertechcapture.Utils.JavaMail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fingertech.fingertechcapture.Cadastro;
import com.fingertech.fingertechcapture.MainActivity;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class envarEmail extends AsyncTask {

    private Context context;
    private  Activity activity;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    private ProgressDialog progressDialog;




    private boolean resultadoenvio = true;


    public envarEmail(Context context, Activity activity,  String email, String subject, String message) {

        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.activity = activity;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        enviarEmail();

        return true;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = new ProgressDialog(context);
        progressDialog = ProgressDialog.show(activity,"Cadastro","Finalizando Cadastro",false,false);

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            if(resultadoenvio) {
                activity.getBaseContext().startActivity(new Intent(activity.getBaseContext(), MainActivity.class));
            }else{
                Toast.makeText(activity.getBaseContext(),"Não foi possível finalizar o cadastro",Toast.LENGTH_LONG).show();
            }
        }

    }





    //não usar esse método diretamente , usar em background.

    public void enviarEmail(){
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login_e_senha.login, login_e_senha.senha);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(login_e_senha.login));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);
            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
           resultadoenvio = false;
        }

    }

}
