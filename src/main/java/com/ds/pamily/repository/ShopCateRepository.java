package com.ds.pamily.repository;

import com.ds.pamily.entity.ShopCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ShopCateRepository extends JpaRepository<ShopCate, Long> {
//    @Query("select sc from shopcate sc left outer join shop s on sc. ")
//    List<Object[]> getShopListPage(Pageable pageable);
}