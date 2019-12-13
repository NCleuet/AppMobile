package com.example.appmobile.Retrofit;

import com.example.appmobile.Model.Pokedex;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface IPokemonDex {
    @GET ("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
