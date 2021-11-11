package com.ds.pamily.repository;

import com.ds.pamily.entity.Item;
import com.ds.pamily.entity.ItemCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    @Query("select i from Item i where i.itemCate = :icno ")
//    List<Item> getItemList(Long icno);

    List<Item> findByItemCate(ItemCate itemCate);
}
