package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShopReplyRepository extends JpaRepository<ShopReply, Long> {
    @Modifying
    @Query("delete from ShopReply sr where sr.shop.sid =:sid ")
    void deleteBySid(Long sid);
}
