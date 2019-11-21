package com.fingertech.fingertechcapture.interfaces;

import android.widget.Button;
import android.widget.ImageView;

import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;

public interface imagem_digital {

    void setarImagem(ImageView img, NBioBSPJNI.CAPTURED_DATA capturedData);

}
