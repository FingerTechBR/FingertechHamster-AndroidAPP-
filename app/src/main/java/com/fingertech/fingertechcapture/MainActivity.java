package com.fingertech.fingertechcapture;

import android.Manifest;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fingertech.fingertechcapture.botoes.botoes_captura;
import com.fingertech.fingertechcapture.interfaces.imagem_digital;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements Nitgen.View  {



    protected Drawable mExpandDrawable;


    private AppBarConfiguration mAppBarConfiguration;
    private Nitgen nitgen;
    /*

    private botoes_captura botao = new botoes_captura(null);
    private DialogFragment sampleDialogFragment;

    List<Button> botoesenables;


    //imgview
    @BindView(R.id.iv_digital_1) ImageView iv_digital_1;
    @BindView(R.id.iv_digital_2) ImageView iv_digital_2;

    //botões
    @BindView(R.id.btn_capturar_1) Button btn_capturar_1;
    @BindView(R.id.btn_capturar_2) Button btn_capturar_2;

    @BindView(R.id.btn_autoOn_1) Button btn_autoOn_1;
    @BindView(R.id.btn_autoOn_2) Button btn_autoOn_2;

    @BindView(R.id.btn_iniciar_dispositivo)  Button btn_iniciar_dispositivo;
    //txt
    @BindView(R.id.txt_sdk_verssao) TextView txt_sdk_verssao;



     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        nitgen = new Nitgen(this, this);
        //personalizado

        /*

        onCheckPermission();
        //btn_captura.setEnabled(false);

        //botao.mudarvisibilidadebotao(botoesenables);


         botoesenables =  Arrays.asList(btn_capturar_1, btn_capturar_2, btn_autoOn_1, btn_autoOn_2);

        botao.mudarvisibilidadebotao(botoesenables, this);

*/




    }








    public void onCheckPermission(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissionArray = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                permissionArray.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                permissionArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                permissionArray.add(Manifest.permission.CAMERA);
            }
            if (permissionArray.size() > 0) {
                String[] strArr = new String[permissionArray.size()];
                strArr = permissionArray.toArray(strArr);
                ActivityCompat.requestPermissions(this, strArr, 0);
            }
        }
    }

    @Override
    public void onDeviceConnected() {

    }

    @Override
    public void onDeviceDisconnected() {

    }

    @Override
    public void onCapture(NBioBSPJNI.CAPTURED_DATA capturedData) {

    }

    @Override
    public void onDeviceMessage(String msg) {

    }

    @Override
    public void onInforMessage(String msg) {

    }

    @Override
    public void onVersion(String msg) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setISOButton(boolean enable) {

    }

    @Override
    public void setRAWButton(boolean enable) {

    }



    /*

    @OnClick(R.id.btn_capturar_1)
    public void btn_capturar_1(){

        botao = new botoes_captura(iv_digital_1);
        nitgen.onCapture1(10000);

    }

    @OnClick(R.id.btn_capturar_2)
    public void btn_capturar_2(){

        botao = new botoes_captura(iv_digital_2);
        nitgen.onCapture2(10000);



    }

    @OnClick(R.id.btn_iniciar_dispositivo)
    public void btn_iniciar_dispositivo(){



        nitgen.openDevice();





    }







    @OnClick(R.id.btn_autoOn_1)
    public void Btn_autoOn_1(){

        botao = new botoes_captura(iv_digital_1);
        sampleDialogFragment = new SampleDialogFragment();
        sampleDialogFragment.show(getFragmentManager(), "DIALOG_TYPE_STOP");
        sampleDialogFragment.setCancelable(false);
        nitgen.onAuthCapture1();


    }

    @OnClick(R.id.btn_autoOn_2)
    public void Btn_autoOn_2(){


        botao = new botoes_captura(iv_digital_2);
        sampleDialogFragment = new SampleDialogFragment();
        sampleDialogFragment.show(getFragmentManager(), "DIALOG_TYPE_STOP");
        sampleDialogFragment.setCancelable(false);
        nitgen.onAuthCapture1();



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public void onDeviceConnected() {


        hideLoading();

        btn_iniciar_dispositivo.setText("Fechar dispositivo");
        botao.mudarvisibilidadebotao(botoesenables, this);

    }

    @Override
    public void onDeviceDisconnected() {
        hideLoading();
        botao.mudarvisibilidadebotao(botoesenables, this);
        btn_iniciar_dispositivo.setText("Abrir dispositivo");

    }

    @Override
    public void onCapture(NBioBSPJNI.CAPTURED_DATA capturedData) {
        if (capturedData.getImage() != null) {
            runOnUiThread(() -> {

            botao.setarImagem(botao.getImg(),capturedData);


            });
        }
    }


    @Override
    public void onDeviceMessage(String msg) {

    }

    @Override
    public void onInforMessage(String msg) {
        runOnUiThread(() -> {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public void onVersion(String msg) {
        runOnUiThread(() -> {
            txt_sdk_verssao.setText(msg);
        });


    }

    @Override
    public void showToast(String msg) {
        runOnUiThread(() -> {

            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void showLoading() {

        if (sampleDialogFragment == null) {
            sampleDialogFragment = new SampleDialogFragment();
        }
        sampleDialogFragment.show(getFragmentManager(), "DIALOG_TYPE_PROGRESS");
    }




    @Override
    public void hideLoading() {

        if (sampleDialogFragment != null && "DIALOG_TYPE_PROGRESS".equals(sampleDialogFragment.getTag())) {
            sampleDialogFragment.dismiss();
        }

    }

    @Override
    public void setISOButton(boolean enable) {

    }

    @Override
    public void setRAWButton(boolean enable) {

    }

    @Override
    public void onClickStopBtn(DialogFragment dialogFragment) {

    }

    */

}
