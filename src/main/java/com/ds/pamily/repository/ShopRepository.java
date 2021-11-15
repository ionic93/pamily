package com.ds.pamily.repository;

import com.ds.pamily.entity.Shop;
import com.ds.pamily.repository.search.SearchShopRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ShopRepository extends JpaRepository<Shop,Long>, SearchShopRepository {
    @Query("select s, min(si), count(distinct r) from Shop s " +
            " left outer join ShopImage si on si.shop = s " +
            " left outer join ShopReply r on r.shop = s group by s")
    Page<Object[]> getShopListPage(Pageable pageable); //샵 게시글+이미지+댓글 페이징처리
}
