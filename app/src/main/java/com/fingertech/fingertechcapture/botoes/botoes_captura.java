package com.fingertech.fingertechcapture.botoes;

import android.widget.ImageView;

import com.fingertech.fingertechcapture.interfaces.imagem_digital;
import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

public class botoes_captura  implements imagem_digital {


    public ImageView img;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public botoes_captura(ImageView img){

        this.img = img;
    }

    @Override
    public void setarImagem(ImageView img, NBioBSPJNI.CAPTURED_DATA capturedData) {
        img.setImageBitmap(capturedData.getImage());

    }
}
