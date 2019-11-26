package com.fingertech.fingertechcapture.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fingertech.fingertechcapture.R;

public class CadastroFragment extends Fragment {

    private CadasatroViewModel cadasatroViewModel;
    @BindView(R.id.cadasatro_et_nome)
    EditText cadastro_et_nome;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadasatroViewModel =
                ViewModelProviders.of(this).get(CadasatroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastro, container, false);

        ButterKnife.bind(this,root);



        return root;
    }
}