package com.fingertech.fingertechcapture.ui.gallery;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import com.fingertech.fingertechcapture.Crud.DBConnect;
import com.fingertech.fingertechcapture.MainActivity;
import com.fingertech.fingertechcapture.Models.Usuario;
import com.fingertech.fingertechcapture.Nitgen;
import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.comportamentos_componentes.botoes_captura;
import com.fingertech.fingertechcapture.comportamentos_componentes.campos_texto;
import com.fingertech.fingertechcapture.interfaces.permissoes;
import com.google.android.material.textfield.TextInputLayout;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;


import static android.app.Activity.RESULT_OK;


public class CadastroFragment extends Fragment implements permissoes, Nitgen.View {

    private CadasatroViewModel cadasatroViewModel;
    private Nitgen nitgen;
    private botoes_captura botao;
    private View root;



    @BindView(R.id.cadastro_iv_foto)
    ImageView cadastro_iv_foto;
    @BindView(R.id.cadastro_iv_digital)
    ImageView cadastro_iv_digital;



    @BindView(R.id.cadastro_btn_salvar)
    Button cadastro_btn_salvar;



    @BindView(R.id.cadasatro_et_nome)
    TextView cadasatro_et_nome;
    @BindView(R.id.cadasatro_et__endereco)
    TextView cadasatro_et__endereco;
    @BindView(R.id.cadasatro_et_telefone)
    TextView cadasatro_et_tel;



    @BindView(R.id.cadastro_til_nome)
    TextInputLayout cadastro_til_nome;
    @BindView(R.id.cadastro_til_endereco)
    TextInputLayout cadastro_til_endereco;
    @BindView(R.id.cadastro_til_telefone)
    TextInputLayout cadastro_til_telefone;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private DBConnect dbConnect;
    private String digitalstring;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadasatroViewModel =
                ViewModelProviders.of(this).get(CadasatroViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_cadastro, container, false);
        initConfig();

        return root;
    }


    public void initConfig(){

        ButterKnife.bind(this,root);
        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(getActivity());
        nitgen = MainActivity.nitgen;
        nitgen.setView(this);
        dbConnect = new DBConnect(getContext());
        campos_texto cp = new campos_texto(cadasatro_et_nome);

    }

    @OnClick(R.id.cadastro_iv_digital)
    public void clickDigital(){
        //nitgen.openDevice();
        botao = new botoes_captura(cadastro_iv_digital);
         nitgen.onCapture1(10000);
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



    //caminho + foto
    private String currentPhotoPath;
    private String foto_digital;
    private String[] fotodigital = new String[2];
    //caminho
    private File storageDir;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileNamevariavel = "JPEG_" + timeStamp + "_";
        String imageFileName = imageFileNamevariavel;

        storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/fingertech");
        File dir = new File(storageDir.toString());

        if(!dir.exists()) dir.mkdirs();
        File image = new  File(storageDir.getAbsolutePath()+"/"+imageFileName+".jpg");

        //File arquivo = new File(storageDir.getAbsolutePath()+"/"+imageFileName+".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        fotodigital[0] = storageDir.getAbsolutePath();
        fotodigital[1] = imageFileName;



        return image;
    }




    public void saveFoto() throws IOException{

        //File file = new File(fotodigital[0]+"/digital_"+fotodigital[1]+".jpg");
        File file = new File(
                storageDir+"/digital_"+fotodigital[1]+
                ".jpg"
        );
        Bitmap bitmap;
        BitmapDrawable drawable = (BitmapDrawable) cadastro_iv_digital.getDrawable();
        bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream arquivoparasavar = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        arquivoparasavar.write(baos.toByteArray());
        arquivoparasavar.flush();
        arquivoparasavar.close();

        foto_digital = file.getAbsolutePath();
        //cadastro_iv_foto.setImageBitmap(bitmap);
        Log.i("foto", "saveFoto: "+ file.getAbsolutePath());
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






    @OnClick(R.id.cadastro_btn_salvar)
    public void salvarDb() {

        if(cadasatro_et_nome.getText().toString().isEmpty()){
            cadasatro_et_nome.setError("não pod estar vazio");
            cadastro_til_nome.setError("Nome não pode estar Vazio");

            return;
        }
        //saveFoto();
        Usuario user = new Usuario();
        user.setNome(cadasatro_et_nome.getText().toString());
        user.setEndereco(cadasatro_et__endereco.getText().toString());
        user.setTelefone(cadasatro_et_tel.getText().toString());
        user.setDigital_caminho(foto_digital);
        user.setFoto(currentPhotoPath);
        user.setDigital(digitalstring);


        long result = 0;//dbConnect.salvarUsuario(user);


        if(result > 0 ){
            Toast.makeText(getContext(), "Usuario salvo",Toast.LENGTH_SHORT).show();
            limparareas();
        }else {
            Toast.makeText(getContext(), "Não foi possível salvar usuario",Toast.LENGTH_SHORT).show();
        }

    }

    public void limparareas(){

        cadasatro_et_nome.setText(null);
        cadasatro_et__endereco.setText(null);
        cadasatro_et_tel.setText(null);
        cadastro_iv_foto.setImageResource(R.mipmap.ic_launcher_foreground);
        cadastro_iv_digital.setImageResource(R.mipmap.ic_launcher_foreground);
    }





    @OnTextChanged(R.id.cadasatro_et_nome)
    public void changedTextOnEditPhone() {


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
        botao.setarImagem(capturedData);

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

    @Override
    public void digitalText(String digital) {
        this.digitalstring = digital;
    }


}