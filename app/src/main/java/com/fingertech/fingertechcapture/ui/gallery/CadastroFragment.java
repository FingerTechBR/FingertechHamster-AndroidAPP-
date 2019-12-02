package com.fingertech.fingertechcapture.ui.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.interfaces.permissoes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.FileProvider.getUriForFile;

public class CadastroFragment extends Fragment implements permissoes {

    private CadasatroViewModel cadasatroViewModel;
    @BindView(R.id.cadasatro_et_nome)
    EditText cadastro_et_nome;

    @BindView(R.id.cadastro_iv_foto)
    ImageView cadastro_iv_foto;



    // permissoes requeridas aqui







    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadasatroViewModel =
                ViewModelProviders.of(this).get(CadasatroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastro, container, false);

        ButterKnife.bind(this,root);

/*
        if((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED )
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
*/

        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);

        sp.solicitarpermissao(getActivity());


        return root;
    }





    @OnClick(R.id.cadastro_iv_foto)
    public void clickfoto(){
        capturarFoto();
    }

    public final String TAG = "cadastrofoto";
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
                        getActivity().getBaseContext().getPackageName()+".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
                Log.e("foto2",photoURI.toString());
            }

        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name

        //File direct = new File(Environment.getExternalStorageDirectory() + "/fingertech4/fingertech2");



        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =  getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */

        );
        Log.e("foto",image.getAbsolutePath().toString());

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void salvarFoto(){



      //  File imagem = new File(dir.getPath() + "/" + System.currentTimeMillis() + ".jpg");
        //uri  = Uri.fromFile(imagem);





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){

            Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            //cadastro_iv_foto.setImageBitmap(imageBitmap);

        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String[] permissoesnecessarias() {


         String[] APPPERMISSOES = {

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
         return APPPERMISSOES;

    }
}