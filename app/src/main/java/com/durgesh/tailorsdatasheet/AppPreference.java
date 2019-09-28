package com.durgesh.tailorsdatasheet;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Durgesh Parekh on 12/17/17.
 */

public class AppPreference {
    private static final String PREFERENCE_FILE_NAME = "AppPreference";
    public boolean firstTimeInstalled=true;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AppPreference(Context context) {

        preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
        firstTimeInstalled = preferences.getBoolean("firstTimeInstalled",true);
    }

    public boolean isFirstTimeInstalled() {
        return firstTimeInstalled;
    }

    public void setFirstTimeInstalled(boolean firstTimeInstalled) {
        this.firstTimeInstalled = firstTimeInstalled;
        editor.putBoolean("firstTimeInstalled",firstTimeInstalled);
        editor.commit();
    }
}
