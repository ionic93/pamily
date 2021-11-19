package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {
    @Modifying
    @Query("delete from ShopImage si where si.shop.sid =:sid ")
    void deleteShopImageBySid(Long sid);
}
