package com.durgesh.tailorsdatasheet;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Durgesh Parekh on 01/18/18.
 */

public class MyApplication extends Application {
    Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        //setup realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("tailorDataSheet.realm").build();
        realm = Realm.getInstance(configuration);
    }
}
