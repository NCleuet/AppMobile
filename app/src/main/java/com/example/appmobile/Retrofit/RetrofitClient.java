package com.example.appmobile.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitClient {
    public static Retrofit instace;

    public static Retrofit getInstace(){
        if(instace == null) {
            new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return instace;

    }
}
