package com.fingertech.fingertechcapture.comportamentos_componentes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class campos_texto  implements TextWatcher {

    public TextView textView;




    public campos_texto(TextView textView){
        this.textView = textView;
        textView.addTextChangedListener(this);
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if(textView.length()== 0 ) textView.setError("Campo "+textView.getHint()+" não pode ser vazio");

    }

    @Override
    public void afterTextChanged(Editable editable) {


    }
}
