package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ShopItem, Long> {
}
