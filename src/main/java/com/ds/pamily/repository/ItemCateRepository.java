package com.ds.pamily.repository;

import com.ds.pamily.dto.ItemCateDTO;
import com.ds.pamily.entity.ItemCate;
import com.ds.pamily.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCateRepository extends JpaRepository<ItemCate, Long> {
//    @Query("select ic from ItemCate where ic.itemType = :itemType ")
//    List<ItemCateDTO> findByItemType(ItemType itemType);
}
