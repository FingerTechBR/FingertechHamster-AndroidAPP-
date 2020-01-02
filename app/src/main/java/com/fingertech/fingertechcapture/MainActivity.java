package com.fingertech.fingertechcapture;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationView;


import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;


public class MainActivity extends AppCompatActivity implements Nitgen.View  {



    protected Drawable mExpandDrawable;
    private NavController navController;


    private AppBarConfiguration mAppBarConfiguration;
    public static Nitgen nitgen;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        this.nitgen = new Nitgen(this, this);
        Menu menuNav = navigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.nav_tools);
        nav_item2.setEnabled(false);
    }





    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
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

    @Override
    public void digitalText(String digital) {

    }

    @Override
    public void resultadoIndexSearch(long id) {

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





}
