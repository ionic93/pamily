package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ShopCate, Long> {
}
