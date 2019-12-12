package com.fingertech.fingertechcapture.data.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConfig {


  public  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.smartabis.com/smartabis/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}
