package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopCateRepository extends JpaRepository<ShopCate, Long> {

}
