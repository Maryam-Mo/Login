package com.example.maryam.log_in.resource;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by maryam on 9/24/19.
 */

public class RealmInstanceGenerator {
    public Realm generateRealmInstance() {
        try {
            return Realm.getDefaultInstance();

        } catch (Exception e) {

            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            return Realm.getInstance(config);
        }
    }
}
