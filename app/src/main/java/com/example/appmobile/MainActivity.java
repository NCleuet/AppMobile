package com.example.appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.appmobile.Common.Common;
import com.example.appmobile.Model.Pokemon;

public class MainActivity extends AppCompatActivity {

        Toolbar toolbar;

    BroadcastReceiver showPokemonType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_POKEMON_TYPE))
            {
                //Replace Fragment
                Fragment pokemonType = PokemonType.getInstance();
                String type = intent.getStringExtra("type");
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                pokemonType.setArguments(bundle);

                getSupportFragmentManager().popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragment, pokemonType);
                fragmentTransaction.addToBackStack("type");
                fragmentTransaction.commit();

                toolbar.setTitle("POKEMON TYPE "+type.toUpperCase());
            }
        }
    };

        BroadcastReceiver showDetail = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().toString().equals(Common.KEY_ENABLE_HOME))
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable back button on Toolbar
                    getSupportActionBar().setDisplayShowHomeEnabled(true); //too

                    //Replace Fragment
                    Fragment detailFragment = PokemonDetail.getInstance();
                    String num = intent.getStringExtra("num");
                    Bundle bundle = new Bundle();
                    bundle.putString("num",num);
                    detailFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment);
                    fragmentTransaction.addToBackStack("detail");
                    fragmentTransaction.commit();
                    //Set Pokemon name for Toolbar
                    Pokemon pokemon = Common.findPokemonByNum(num);
                    toolbar.setTitle(pokemon.getName());
                }
            }
        };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_NUM_EVOLUTION))
            {

                //Replace Fragment
                Fragment detailFragment = PokemonDetail.getInstance();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num",num);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailFragment); //Remove current Fragment
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set Pokemon name for Toolbar
                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("POKEMON LIST");
        setSupportActionBar(toolbar);

        //Register Broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail,new IntentFilter(Common.KEY_ENABLE_HOME));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution,new IntentFilter(Common.KEY_NUM_EVOLUTION));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showPokemonType,new IntentFilter(Common.KEY_POKEMON_TYPE));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                toolbar.setTitle("POKEMON LIST");

                //Clear all fragment detail and pop to list fragment
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE);



                // Replace Fragment

                Fragment pokemonList = PokemonList.getInstance();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(pokemonList);
                fragmentTransaction.replace(R.id.list_pokemon_fragment,pokemonList);
                fragmentTransaction.commit();

                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                
                break;
                default:
                    break;
        }
        return true;
    }
}
