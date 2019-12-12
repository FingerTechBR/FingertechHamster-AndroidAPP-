package com.fingertech.fingertechcapture.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fingertech.fingertechcapture.MainActivity;
import com.fingertech.fingertechcapture.Nitgen;
import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.SampleDialogFragment;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.comportamentos_componentes.botoes_captura;
import com.fingertech.fingertechcapture.interfaces.permissoes;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fingertech.fingertechcapture.Nitgen.status;

public class HomeFragment extends Fragment implements SampleDialogFragment.SampleDialogListener, Nitgen.View, permissoes {

    private HomeViewModel homeViewModel;

    private Nitgen nitgen;
    private botoes_captura botao = new botoes_captura(null);
    private SampleDialogFragment sampleDialogFragment;


    List<Button> botoesenables;
    View root;


    //imgview
    @BindView(R.id.iv_digital_1)
    ImageView iv_digital_1;
    @BindView(R.id.iv_digital_2) ImageView iv_digital_2;

    //botões
    @BindView(R.id.btn_capturar_1) Button btn_capturar_1;
    @BindView(R.id.btn_capturar_2) Button btn_capturar_2;

    @BindView(R.id.btn_autoOn_1) Button btn_autoOn_1;
    @BindView(R.id.btn_autoOn_2) Button btn_autoOn_2;

    @BindView(R.id.btn_iniciar_dispositivo)  Button btn_iniciar_dispositivo;
    //txt
    @BindView(R.id.txt_sdk_verssao) TextView txt_sdk_verssao;







    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_home, container, false);
         initConfigs();

        return root;
    }



    //aqui configurações iniciais
    public void initConfigs(){


        ButterKnife.bind(this,root);
        botoesenables =  Arrays.asList(btn_capturar_1, btn_capturar_2, btn_autoOn_1, btn_autoOn_2);
        nitgen = MainActivity.nitgen;
        nitgen.setView(this);
        if(!status) {

            botao.mudarvisibilidadebotao(botoesenables, getActivity());
        }else{
            btn_iniciar_dispositivo.setText("Fechar Dispositivo");
        }
        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
    }



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
        sampleDialogFragment =  new SampleDialogFragment();
        sampleDialogFragment.setView(this);
        sampleDialogFragment.show(getActivity().getFragmentManager(), "DIALOG_TYPE_STOP");
        sampleDialogFragment.setCancelable(false);
        nitgen.onAuthCapture1();


    }

    @OnClick(R.id.btn_autoOn_2)
    public void Btn_autoOn_2(){

        botao = new botoes_captura(iv_digital_2);
        sampleDialogFragment = new SampleDialogFragment();
        sampleDialogFragment.setView(this);
        sampleDialogFragment.show(getActivity().getFragmentManager(), "DIALOG_TYPE_STOP");
        sampleDialogFragment.setCancelable(false);
        nitgen.onAuthCapture1();



    }






    @Override
    public void onDeviceConnected() {


        hideLoading();

        btn_iniciar_dispositivo.setText("Fechar dispositivo");
        botao.mudarvisibilidadebotao(botoesenables, getActivity());

    }

    @Override
    public void onDeviceDisconnected() {
        hideLoading();
        botao.mudarvisibilidadebotao(botoesenables, getActivity());
        btn_iniciar_dispositivo.setText("Abrir dispositivo");

    }

    @Override
    public void onCapture(NBioBSPJNI.CAPTURED_DATA capturedData) {
        if (capturedData.getImage() != null) {
            getActivity().runOnUiThread(() -> {

                botao.setarImagem(capturedData);


            });
        }
    }


    @Override
    public void onDeviceMessage(String msg) {

    }

    @Override
    public void onInforMessage(String msg) {
        getActivity().runOnUiThread(() -> {
            //Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public void onVersion(String msg) {
        getActivity().runOnUiThread(() -> {
            txt_sdk_verssao.setText(msg);
        });


    }

    @Override
    public void showToast(String msg) {
        getActivity().runOnUiThread(() -> {

            //Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void showLoading() {

        if (sampleDialogFragment == null) {
            sampleDialogFragment = new SampleDialogFragment();
        }
        sampleDialogFragment.show(getActivity().getFragmentManager(), "DIALOG_TYPE_PROGRESS");
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
    public void digitalText(String digital) {

    }

    @Override
    public void resultadoIndexSearch(long id) {

    }


    @Override
    public void onClickStopBtn() {
        Toast.makeText(getActivity(),"onstopclick",Toast.LENGTH_SHORT).show();
        hideLoading();
        nitgen.onCaptureCancel();


    }

    @Override
    public String[] permissoesnecessarias() {

        String[] APPPERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };
        return APPPERMISSIONS;
    }
}


