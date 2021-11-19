package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopReply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopReplyRepositoryTest {
    @Autowired
    private ShopReplyRepository shopReplyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 50).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;

            Long sid = (long)(Math.random()*30)+1;
            ShopReply shopReply = ShopReply.builder()
                    .text("댓글 남겨요"+i)
                    .member(Member.builder().mid(mid).build())
                    .shop(Shop.builder().sid(sid).build())
                    .build();
            shopReplyRepository.save(shopReply);
        });
    }

    @Test
    public void deleteReply() {
        shopReplyRepository.deleteAll();
    }
}