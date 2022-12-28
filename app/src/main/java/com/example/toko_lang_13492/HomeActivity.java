package com.example.toko_lang_13492;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.toko_lang_13492.fragment.AddFragment;
import com.example.toko_lang_13492.fragment.CartFragment;
import com.example.toko_lang_13492.fragment.CatFragment;
import com.example.toko_lang_13492.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    void init(){
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    HomeFragment homeFragment = new HomeFragment();
    CatFragment catFragment = new CatFragment();
    CartFragment cartFragment = new CartFragment();
    AddFragment addFragment = new AddFragment();
    Fragment fragment = null;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                fragment = homeFragment;
                break;

            case R.id.category:
                fragment = catFragment;
                break;

            case R.id.cart:
                fragment = cartFragment;
                break;
            case R.id.add:
                fragment = addFragment;
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
}