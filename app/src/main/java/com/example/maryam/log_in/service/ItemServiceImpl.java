package com.example.maryam.log_in.service;

import com.example.maryam.log_in.UserApi;
import com.example.maryam.log_in.repository.ItemRepository;

/**
 * Created by maryam on 9/24/19.
 */

class ItemServiceImpl implements ItemService {

    private final UserApi userApi;
    private final ItemRepository itemRepository;

    ItemServiceImpl(UserApi userApi, ItemRepository itemRepository) {
        this.userApi = userApi;
        this.itemRepository = itemRepository;
    }

    @Override
    public void createItem() {

    }
}
