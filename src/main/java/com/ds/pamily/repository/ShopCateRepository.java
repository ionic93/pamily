package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopCate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopCateRepository extends JpaRepository<ShopCate, Long> {
    List<ShopCate> findAll();

    @Query("select c from ShopCate c where c.cateName = :catename")
    Optional<ShopCate> findByCateName(String catename);
}