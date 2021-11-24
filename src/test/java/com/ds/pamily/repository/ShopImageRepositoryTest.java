package com.ds.pamily.repository;

import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopImageRepositoryTest {
    @Autowired
    private ShopImageRepository shopImageRepository;

    @Test
    public void deleteImage() {
        Long sid = (long)104;
        shopImageRepository.deleteShopImageBySid(sid);
    }
}