package com.example.maryam.log_in.user.realm;

import com.example.maryam.log_in.login.realm.RealmLogin;
import com.example.maryam.log_in.login.realm.RealmLoginImpl;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;

/**
 * Created by maryam on 9/25/19.
 */

public enum RealmUs {
    INSTANCE;

    private RealmUser realmUser;

    public RealmUser getRealmUser() {
        if (realmUser == null){
            realmUser = new RealmUserImpl(RealmInstanceGenerator.INSTANCE.generateRealmInstance());
        }
        return realmUser;
    }
}
