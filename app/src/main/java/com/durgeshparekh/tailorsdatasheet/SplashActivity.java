package com.durgeshparekh.tailorsdatasheet;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.durgeshparekh.tailorsdatasheet.utils.AppPreference;
import com.durgeshparekh.tailorsdatasheet.utils.SharedPrefManager;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkRuntimePermission();
    }

    private void checkRuntimePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            //request runtime permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, MY_PERMISSION_REQUEST_CODE);

        }else {

            fetchID();
        }

    }

    private void fetchID() {

        AppPreference preference = new AppPreference(getApplicationContext());

        if(preference.isFirstTimeInstalled()){
            deletePreviousData();
            preference.setFirstTimeInstalled(false);
        }

        String ans = SharedPrefManager.getInstance(this).getShopName();
        if(ans != null){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("shopName", ans);
            startActivity(i);
            finish();
        }

        final TextInputLayout shopName = findViewById(R.id.shop_name);
        Button saveShopName = findViewById(R.id.save_shop_name);

        saveShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop_name = shopName.getEditText().getText().toString();

                if(shop_name.isEmpty()){
                    Toast.makeText(SplashActivity.this, "Please enter your shop name", Toast.LENGTH_SHORT).show();
                }else {

                    SharedPrefManager.getInstance(SplashActivity.this).saveShopName(shop_name);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("shopName", shop_name);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void deletePreviousData(){
        String root = Environment.getExternalStorageDirectory() + "/TailorsDataSheet/";
        File rootFile = new File(root);

        if (rootFile.exists()) {
            deleteFileFolder(rootFile);
        }
    }

    private void deleteFileFolder(File file) {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles.length > 0) {
                for (File subFile : subFiles) {
                    if (!subFile.isDirectory())
                        deleteFileFolder(subFile);
                }
            }
        }
        file.delete();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchID();
            }
        }
    }
}
