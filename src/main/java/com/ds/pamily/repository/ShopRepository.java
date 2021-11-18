package com.ds.pamily.repository;

import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.repository.search.SearchShopRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long>,SearchShopRepository{
    @Query("select s, min(si), count(distinct r) from Shop s " +
            " left outer join ShopImage si on si.shop = s " +
            " left outer join ShopReply r on r.shop = s group by s")
    Page<Object[]> getShopListPage(Pageable pageable); // 게시글+이미지(1개)+댓글수 페이징처리

    @Query("select s, si, count(r) from Shop s left outer join ShopImage si on si.shop = s " +
            " left outer join ShopReply r on r.shop = s " +
            " where s.sid = :sid group by si")
    List<Object[]> getShopWithAll(Long sid); //특정 게시물 조회

    @Query(value = "select s, m, c, min(si), count(r) From Shop s left join s.member m left join s.scno c left join ShopImage si on si.shop = s " +
            " left join ShopReply r on r.shop = s " +
            " group by s ", countQuery = "select count(s) From Shop s ")
    Page<Object[]> getShopWithAll(Pageable pageable); //목록 화면에 필요한 JPQL

}
