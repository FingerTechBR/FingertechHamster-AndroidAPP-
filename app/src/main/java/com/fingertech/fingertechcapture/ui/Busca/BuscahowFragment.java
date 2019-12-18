package com.fingertech.fingertechcapture.ui.Busca;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
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

import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.data.CRUD.DBConnect;
import com.fingertech.fingertechcapture.MainActivity;
import com.fingertech.fingertechcapture.Models.Usuario;
import com.fingertech.fingertechcapture.Nitgen;
import com.fingertech.fingertechcapture.R;

import com.fingertech.fingertechcapture.interfaces.permissoes;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuscahowFragment extends Fragment implements Nitgen.View, permissoes {

    private BuscahowViewModel slideshowViewModel;
    private Nitgen nitgen;
    private View root;
    private AlertDialog dialog;
    private List<Usuario> users;



    @BindView(R.id.busca_btn_buscardigital)
    Button busca_btn_buscardigital;

    @BindView(R.id.busca_iv_digital)
    ImageView busca_iv_digital;

    @BindView(R.id.busca_iv_foto)
    ImageView busca_iv_foto;

    @BindView(R.id.busca_et_nome)
    TextView busca_et_nome;

    @BindView(R.id.busca_et__endereco)
    TextView busca_et__endereco;

    @BindView(R.id.busca_et_telefone)
    TextView busca_et_telefone;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(BuscahowViewModel.class);
        root = inflater.inflate(R.layout.fragment_busca, container, false);
        initConfig();
        return root;
    }



    public void initConfig(){


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(getActivity());
        ButterKnife.bind(this,root);
        nitgen = MainActivity.nitgen;
        nitgen.setView(this);
        popularDB();
    }





    @OnClick(R.id.busca_btn_buscardigital)
    public void busca_btn_buscardigital(){
        AlertDialog.Builder msgbox = new AlertDialog.Builder(getActivity());
        msgbox.setTitle("Ensira a digital");
        msgbox.setIcon(android.R.drawable.ic_menu_search);
        msgbox.setMessage("Ensira a digital");
        dialog = msgbox.show();
        TextView msgdialog = (TextView) dialog.findViewById(android.R.id.message);
        msgdialog.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        enserirDigital();
    }

    public void popularDB(){

        DBConnect dbConnect = new DBConnect(getContext());
        users = new ArrayList<>();
        users = dbConnect.buscaTodos();
        for(Usuario user : users){
            if(user.getDigital() != null)
            nitgen.onAddFIRstring(user.getDigital(),user.getId());
        }
    }


    public void enserirDigital(){


        nitgen.identify(5000);

    }

    private void setPic(ImageView imageView, String foto) {
        // Get the dimensions of the View

        File caminho = new File(foto);
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(caminho.getAbsolutePath(), bmOptions);
        imageView.setImageBitmap(bitmap);
    }


    public void preenchercampos(long id) {

        for (Usuario user : users) {
            if (user.getId() == id) {
                setPic(busca_iv_foto, user.getFoto());
                setPic(busca_iv_digital, user.getDigital_caminho());
                busca_et_nome.setText(user.getNome());
                busca_et__endereco.setText(user.getEndereco());
                busca_et_telefone.setText(user.getTelefone());
                return;
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

        dialog.dismiss();
        nitgen.onCaptureCancel();

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

        getActivity().runOnUiThread(() -> {
            dialog.dismiss();
            Toast.makeText(getActivity(),"usuario "+msg,Toast.LENGTH_LONG).show();
        });
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

        getActivity().runOnUiThread(() -> {
        //Toast.makeText(getActivity(),digital,Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void resultadoIndexSearch(long id) {

        if(id < 0){
            Toast.makeText(getActivity(),"Usuário não Encontrado",Toast.LENGTH_LONG).show();

        }else{
            preenchercampos(id);
        }

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
}