package com.fingertech.fingertechcapture.data.Rest.interfaces;

import com.fingertech.fingertechcapture.Models.Usuario;

import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.http.POST;

public interface POSTfacial {


    @POST("/abis/identify")
    Call<Usuario> buscaFacial(@Handler("Authorization") String authHeader);

}
