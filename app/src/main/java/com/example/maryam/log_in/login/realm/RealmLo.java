package com.example.maryam.log_in.login.realm;

import com.example.maryam.log_in.login.realm.RealmLogin;
import com.example.maryam.log_in.login.realm.RealmLoginImpl;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;

/**
 * Created by maryam on 9/25/19.
 */

public enum RealmLo {
    INSTANCE;

    private RealmLogin realmLogin;

    public RealmLogin getRealmLogin() {
        if (realmLogin == null){
            realmLogin = new RealmLoginImpl(RealmInstanceGenerator.INSTANCE.generateRealmInstance());
        }
        return realmLogin;
    }
}
