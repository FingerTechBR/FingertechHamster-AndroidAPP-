package com.fingertech.fingertechcapture.ui.share;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fingertech.fingertechcapture.R;
import com.fingertech.fingertechcapture.Utils.solicita_permissao;
import com.fingertech.fingertechcapture.interfaces.permissoes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareFragment extends Fragment implements permissoes {

    private ShareViewModel shareViewModel;

    private View root;


    @BindView(R.id.share_wv)
    WebView share_wv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_share, container, false);
        initConfig();
        return root;
    }


    public void initConfig(){

        solicita_permissao sp = new solicita_permissao(this::permissoesnecessarias);
        sp.solicitarpermissao(getActivity());
        ButterKnife.bind(this,root);

        share_wv.loadUrl("http://fingertech.com.br");
        WebSettings webSettings = share_wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        share_wv.setWebChromeClient(new WebChromeClient());
        share_wv.setWebViewClient(new WebViewClient());
        share_wv.getSettings().setDomStorageEnabled(true);
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
