package com.durgeshparekh.tailorsdatasheet.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefManager {

    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    private Context context;
    public static final String SHARED_PREF_NAME = "tailorsDataSheet";

    private SharedPrefManager(Context mCtx) {
        context = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveShopName(String shopName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putString("shopName", shopName);

        editor.apply();
    }

    public String getShopName(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        return sharedPreferences.getString("shopName", null);
    }

}
