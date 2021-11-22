package com.ds.pamily.repository;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public void insertAdopt() {
        IntStream.rangeClosed(1,30).forEach(i->{
            Long mid = (long)(Math.random()*30)+1;
            Long scno = (long)1;
            Shop shop = Shop.builder()
                    .title("분양해요"+i)
                    .content("분양해요..."+i)
                    .member(Member.builder().mid(mid).build())
                    .scno(ShopCate.builder().scno(scno).build())
                    .build();
            shopRepository.save(shop);

//            int count = (int)(Math.random()*3)+1;
//
//            for (int j = 0; j < count; j++) {
//                ShopImage shopImage = ShopImage.builder()
//                        .uuid(UUID.randomUUID().toString())
//                        .shop(shop)
//                        .imgName("test"+j+".jpg")
//                        .build();
//                shopImageRepository.save(shopImage);
//            }
            ShopImage shopImage = ShopImage.builder()
                    .uuid(UUID.randomUUID().toString())
                    .shop(shop)
                    .imgName("test"+i+".jpg")
                    .build();
            shopImageRepository.save(shopImage);
        });
    }

    @Test
    public void insertAdoption() {
        IntStream.rangeClosed(1,30).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;
            Long scno = (long)2;
            Shop shop = Shop.builder()
                    .title("입양해요"+i)
                    .content("입양해요..."+i)
                    .member(Member.builder().mid(mid).build())
                    .scno(ShopCate.builder().scno(scno).build())
                    .build();
            shopRepository.save(shop);

            ShopImage shopImage = ShopImage.builder()
                    .uuid(UUID.randomUUID().toString())
                    .shop(shop)
                    .imgName("test"+i+".jpg")
                    .build();
            shopImageRepository.save(shopImage);
        });
    }

    @Test
    public void insertMissing() {
        IntStream.rangeClosed(1,30).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;
            Long scno = (long)3;
            Shop shop = Shop.builder()
                    .title("동물 찾아요"+i)
                    .content("동물 찾아요..."+i)
                    .member(Member.builder().mid(mid).build())
                    .scno(ShopCate.builder().scno(scno).build())
                    .build();
            shopRepository.save(shop);

            ShopImage shopImage = ShopImage.builder()
                    .uuid(UUID.randomUUID().toString())
                    .shop(shop)
                    .imgName("test"+i+".jpg")
                    .build();
            shopImageRepository.save(shopImage);
        });
    }

    @Test
    public void insertShop() {
        IntStream.rangeClosed(1,30).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;
            Long scno = (long)4;
            Shop shop = Shop.builder()
                    .title("테스트 사요..."+i)
                    .content("물건 삽니다..."+i)
                    .member(Member.builder().mid(mid).build())
                    .scno(ShopCate.builder().scno(scno).build())
                    .build();
            shopRepository.save(shop);

            ShopImage shopImage = ShopImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .shop(shop)
                        .imgName("test"+i+".jpg")
                        .build();
                shopImageRepository.save(shopImage);
        });
    }
//
//
//    @Test
//    public void deleteAll() {
//        shopRepository.deleteAll();
//    }
//
//    @Test
//    public void deleteImage() {
//        shopImageRepository.deleteAll();
//    }
//
//    @Test
//    public void modify() {
//        Optional<Shop> result = shopRepository.findById(41L);
//        if (result.isPresent()) {
//            Shop shop = result.get();
//
//            shop.changeShopTitle("수정됨...");
//            shop.changeShopContent("글 수정됨");
//            shopRepository.save(shop);
//        }
//    }
//    @Transactional
//    @Test
//    public void testSearch() {
//        Pageable pageable = PageRequest.of(0,10, Sort.by("sid").descending());
//        String type = "t";
//        String keyword = "30";
//
//        Page<Object[]> result = shopRepository.searchPage(type,keyword,pageable);
//
//        result.get().forEach(row->{
//            Object[] arr = (Object[]) row;
//            System.out.println(Arrays.toString(arr));
//        });
//    }
//
//    @Transactional
//    @Test
//    public void testListPage() {
//        Pageable pageable = PageRequest.of(0,10, Sort.by("sid").descending());
//
//        Page<Object[]> result = shopRepository.getShopListPage(pageable);
//
//        result.get().forEach(row->{
//            Object[] arr = (Object[]) row;
//            System.out.println(Arrays.toString(arr));
//        });
//    }

}