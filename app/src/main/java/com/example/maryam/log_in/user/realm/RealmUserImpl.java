package com.example.maryam.log_in.user.realm;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by maryam on 10/1/19.
 */

public class RealmUserImpl implements RealmUser {
    private final Realm realmInstance;

    public RealmUserImpl(Realm realmInstance) {
        this.realmInstance = realmInstance;
    }


    @Override
    public List<User> findALl() {
        return realmInstance.where(User.class).findAll();
    }

    @Override
    public void create(final List<User> users) {
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(users);
            }
        });
    }
}