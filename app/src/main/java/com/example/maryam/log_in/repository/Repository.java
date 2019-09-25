package com.example.maryam.log_in.repository;

import com.example.maryam.log_in.resource.RealmInstanceGenerator;

import io.realm.Realm;

/**
 * Created by maryam on 9/24/19.
 */

public enum Repository {
    INSTANCE;

    private ItemRepository itemRepository;

    public ItemRepository getItemRepository(){
        if (itemRepository == null){
            itemRepository = new ItemRepositoryImpl(RealmInstanceGenerator.INSTANCE.generateRealmInstance());
        }
        return itemRepository;
    }
}
