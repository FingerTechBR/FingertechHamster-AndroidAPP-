package com.fingertech.fingertechcapture.botoes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fingertech.fingertechcapture.interfaces.imagem_digital;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

import java.util.List;
import java.util.logging.Logger;

public class botoes_captura  extends Activity implements imagem_digital {


    private ImageView img;



    private Activity activity;




    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public botoes_captura(ImageView img){

        this.img = img;
    }


    public void setactivity(Activity activity){

        this.activity = activity;

    }

    //mudar status dos botoes
    public void mudarvisibilidadebotao(List<Button> bt, Activity activity){

       
        for(int c = 0; c < bt.size(); c++){



           Button button = bt.get(c);
            Log.w("button", String.valueOf(button.getId()));

            if(button.isEnabled()){

                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }

    }


    @Override
    public void setarImagem(ImageView img, NBioBSPJNI.CAPTURED_DATA capturedData) {

        img.setImageBitmap(capturedData.getImage());

    }
}
