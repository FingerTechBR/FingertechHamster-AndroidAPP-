package com.fingertech.fingertechcapture.ui.Busca;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fingertech.fingertechcapture.Crud.DBConnect;
import com.fingertech.fingertechcapture.MainActivity;
import com.fingertech.fingertechcapture.Models.Usuario;
import com.fingertech.fingertechcapture.Nitgen;
import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuscahowFragment extends Fragment implements Nitgen.View {

    private BuscahowViewModel slideshowViewModel;
    private Nitgen nitgen;
    private View root;


    @BindView(R.id.busca_btn_buscardigital)
    Button busca_btn_buscardigital;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(BuscahowViewModel.class);
        root = inflater.inflate(R.layout.fragment_busca, container, false);
        initConfig();
        return root;
    }


    public void initConfig(){


        ButterKnife.bind(this,root);
        nitgen = MainActivity.nitgen;
        nitgen.setView(this);

    }


    @OnClick(R.id.busca_btn_buscardigital)
    public void busca_btn_buscardigital(){
        popularDB();
    }

    public void popularDB(){

        DBConnect dbConnect = new DBConnect(getContext());
        List<Usuario> users = new ArrayList<>();
        users = dbConnect.buscaTodos();
        for(Usuario user : users){
            Log.i("usuario", "popularDB: "+ user.toString());
            nitgen.onAddFIR(user.getDigital(),user.getId());
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

    @Override
    public void digitalText(String digital) {

    }
}