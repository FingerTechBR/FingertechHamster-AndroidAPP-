package com.fingertech.fingertechcapture.ui.gallery;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.fingertech.fingertechcapture.MainActivity;
import com.fingertech.fingertechcapture.Nitgen;
import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.botoes.botoes_captura;
import com.fingertech.fingertechcapture.interfaces.permissoes;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;


import static android.app.Activity.RESULT_OK;


public class CadastroFragment extends Fragment implements permissoes, Nitgen.View {

    private CadasatroViewModel cadasatroViewModel;
    private Nitgen nitgen;
    private botoes_captura botao;


    @BindView(R.id.cadasatro_et_nome)
    EditText cadastro_et_nome;

    @BindView(R.id.cadastro_iv_foto)
    ImageView cadastro_iv_foto;

    @BindView(R.id.cadastro_iv_digital)
    ImageView cadastro_iv_digital;



    static final int REQUEST_IMAGE_CAPTURE = 1;









    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadasatroViewModel =
                ViewModelProviders.of(this).get(CadasatroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastro, container, false);

        ButterKnife.bind(this,root);
        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(getActivity());
       nitgen = MainActivity.nitgen;
       nitgen.setView(this);



        return root;
    }









    @OnClick(R.id.cadastro_iv_digital)
    public void clickDigital(){
        //nitgen.openDevice();
         nitgen.onCapture1(10000);
        botao = new botoes_captura(cadastro_iv_digital);
        Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();




    }

    @OnClick(R.id.cadastro_iv_foto)
    public void clickfoto(){
        capturarFoto();

    }

   ;
    public void capturarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getBaseContext(),
                        getActivity().getBaseContext().getPackageName()+".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
                //Log.e("foto2",photoFile.toString());
            }
        }
    }



    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/fingertech");
        File dir = new File(storageDir.toString());

        if(!dir.exists()) dir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }





    private void setPic() {
        // Get the dimensions of the View
        int targetW = cadastro_iv_foto.getWidth();
        int targetH = cadastro_iv_foto.getHeight();

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
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        cadastro_iv_foto.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            setPic();
        }
    }

    @Override
    public String[] permissoesnecessarias() {


         String[] APPPERMISSOES = {

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
         return APPPERMISSOES;

    }

    @Override
    public void onDeviceConnected() {

    }

    @Override
    public void onDeviceDisconnected() {

    }

    @Override
    public void onCapture(NBioBSPJNI.CAPTURED_DATA capturedData) {


        //botao.setarImagem(capturedData);


    }

    @Override
    public void onDeviceMessage(String msg) {

    }

    @Override
    public void onInforMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVersion(String msg) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();

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
}