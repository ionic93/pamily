package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {
    @Transactional
    @Modifying
    @Query("delete from ShopImage si where si.shop.sid =:sid ")
    void deleteShopImageBySid(Long sid);

    @Query("select si from ShopImage si where si.shop.sid =:sid ")
    List<ShopImage> findAllByShop(Long sid);
}
