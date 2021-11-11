package com.ds.pamily.repository;

import com.ds.pamily.entity.ItemCate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class ItemCateRepositoryTest {
    @Autowired
    ItemCateRepository itemCateRepository;

    @Test
    void getItemCateList() {
        Optional<ItemCate> itemCate = itemCateRepository.findById(3L);
        System.out.println(">>"+itemCate);
    }
}