package com.ds.pamily.repository;

import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopCateRepositoryTest {
    @Autowired
    private ShopCateRepository shopCateRepository;

    @Test
    public void insertCate() {
        String[] cate = {"분양","악세사리","사료","기타"};
        IntStream.rangeClosed(0,3).forEach(i->{
            ShopCate shopCate = ShopCate.builder()
                    .cateName(cate[i])
                    .build();
            System.out.println(shopCateRepository.save(shopCate));
        });
    }

}