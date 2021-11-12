package com.ds.pamily.repository;

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

    @Transactional
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("sid").descending());

        QShop qShop = QShop.shop;

        String keyword = "사료";


        BooleanBuilder builder = new BooleanBuilder();

//        BooleanExpression expression = qShop.title.contains(keyword);
        BooleanExpression expression = qShop.scno.cateName.contains("사료");
        builder.and(expression);

        Page<Shop> result = shopRepository.findAll(builder,pageable);
        result.stream().forEach(shop -> {
            System.out.println(shop);
        });
    }

    @Transactional
    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("sid").descending());

        QShop qShop = QShop.shop;

        String keyword = "사료";
        String cont = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exCate = qShop.scno.cateName.contains(keyword);
        BooleanExpression exContent = qShop.content.contains(cont);
        BooleanExpression exAll = exCate.and(exContent);
        builder.and(exAll);
        builder.and(qShop.sid.gt(0L));


        Page<Shop> result = shopRepository.findAll(builder,pageable);
        result.stream().forEach(shop -> {
            System.out.println(shop);
        });
    }
}