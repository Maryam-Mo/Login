package com.example.maryam.log_in.repository;

import com.example.maryam.log_in.dto.Item;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;

import java.util.List;

import io.realm.Realm;

/**
 * Created by maryam on 9/24/19.
 */

public class ItemRepositoryImpl implements ItemRepository {

    private final Realm realmInstance;

    public ItemRepositoryImpl(Realm realmInstance) {
        this.realmInstance = realmInstance;
    }

    @Override
    public void create(final Item item) {
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(item);
            }
        });
    }

    @Override
    public void create(final List<Item> items) {
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(items);
            }
        });
    }

    @Override
    public List<Item> findAll() {
        return realmInstance.where(Item.class).findAll();
    }
}
