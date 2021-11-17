package com.ds.pamily.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface SearchShopRepository {
//    @Query("")
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
