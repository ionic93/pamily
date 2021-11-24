package com.ds.pamily.repository;

import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopCateRepositoryTest {
    @Autowired
    private ShopCateRepository shopCateRepository;

    @Test
    public void insertCate() {
        String[] cate = {"분양","입양","동물찾기","공동구매"};
        IntStream.rangeClosed(0,3).forEach(i->{
            ShopCate shopCate = ShopCate.builder()
                    .cateName(cate[i])
                    .build();
            System.out.println(shopCateRepository.save(shopCate));
        });
    }

    @Test
    public void findcate() {
        List<ShopCate> result = shopCateRepository.findAll();
        for (ShopCate c : result) {
            System.out.println(c);
        }
    }

    @Test
    public void findCateName() {
        String cateName = "악세사리";
        Optional<ShopCate> result = shopCateRepository.findByCateName(cateName);
        if (result.isPresent()) {
            System.out.println(result);
        }
    }

}