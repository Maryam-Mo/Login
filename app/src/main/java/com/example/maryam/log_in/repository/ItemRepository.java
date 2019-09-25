package com.example.maryam.log_in.repository;

import com.example.maryam.log_in.dto.Item;

import java.util.List;

/**
 * Created by maryam on 9/24/19.
 */

public interface ItemRepository {
    void create(Item item);
    void create(List<Item> items);
    List<Item> findAll();
}
