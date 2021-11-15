package com.ds.pamily.repository.search;

import com.ds.pamily.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchShopRepository {
    Shop search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
