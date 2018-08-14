package com.iglesias.c.appgym.RestApi.Adapter;



import com.iglesias.c.appgym.RestApi.ConstantesRestApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ciglesias on 31/01/2018.
 */

public class RestApiAdapter {
    public static Retrofit provideRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ConstantesRestApi.BASE_URL)
                .build();
        return retrofit;
    }
}
