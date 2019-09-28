package com.durgesh.tailorsdatasheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {

    TextInputLayout shopName;
    Button saveShopName;
    ConstraintLayout splash_screen;
    Realm myRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String ans = sp.getString("shop_name", null);
        if(ans !=null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        fetchId();
    }

    private void fetchId() {
        splash_screen = (ConstraintLayout) findViewById(R.id.splash_screen);
        shopName = (TextInputLayout) findViewById(R.id.shop_name);
        saveShopName = (Button) findViewById(R.id.save_shop_name);

        saveShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop_name = shopName.getEditText().getText().toString();

                if(shop_name.isEmpty()){
                    Snackbar.make(splash_screen,"Please enter your shop name",Snackbar.LENGTH_SHORT).show();
                }else {

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sp.edit().putString("shop_name", shop_name).apply();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
