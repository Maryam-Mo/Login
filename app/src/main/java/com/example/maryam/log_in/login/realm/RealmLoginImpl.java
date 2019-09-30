package com.example.maryam.log_in.login.realm;

import com.example.maryam.log_in.dto.Item;
import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by maryam on 9/25/19.
 */

public class RealmLoginImpl implements RealmLogin {
    private final Realm realmInstance;

    public RealmLoginImpl(Realm realmInstance) {
        this.realmInstance = realmInstance;
    }

    @Override
    public void create(final User user) {
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(user);
            }
        });
    }

    @Override
    public List<LoginUser> findAll() {
        return realmInstance.where(LoginUser.class).findAll();
    }
}
