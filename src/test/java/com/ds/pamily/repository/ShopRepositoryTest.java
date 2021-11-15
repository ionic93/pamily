package com.ds.pamily.repository;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopRepositoryTest {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopImageRepository shopImageRepository;


    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1,30).forEach(i->{
        Long mid = (long)(Math.random()*10)+1;
        Long scno = (long)(Math.random()*4)+1;
            Shop shop = Shop.builder()
                    .title("테스트 사요..."+i)
                    .content("물건 삽니다..."+i)
                    .member(Member.builder().mid(mid).build())
                    .scno(ShopCate.builder().scno(scno).build())
                    .build();
            shopRepository.save(shop);

            int count = (int)(Math.random()*3)+1;

            for (int j = 0; j < count; j++) {
                ShopImage shopImage = ShopImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .shop(shop)
                        .imgName("test"+j+".jpg")
                        .build();
                shopImageRepository.save(shopImage);
            }
        });
    }

    @Test
    public void deleteAll() {
        shopRepository.deleteAll();
    }

    @Test
    public void modify() {
        Optional<Shop> result = shopRepository.findById(41L);
        if (result.isPresent()) {
            Shop shop = result.get();

            shop.changeShopTitle("수정됨...");
            shop.changeShopContent("글 수정됨");
            shopRepository.save(shop);
        }
    }
    @Test
    public void testSearch1() {
        shopRepository.search1();
    }

    @Transactional
    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("sid").descending());
        Page<Object[]> result = shopRepository.searchPage("t","1",pageable);
    }

    @Test
    public void testGetAll() {
        List<Object[]> result = shopRepository.getShopWithAll(30L);
        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

}