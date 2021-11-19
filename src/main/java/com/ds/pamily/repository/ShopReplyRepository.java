package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopReply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopReplyRepository extends JpaRepository<ShopReply, Long> {
    @Modifying
    @Query("delete from ShopReply sr where sr.shop.sid =:sid ")
    void deleteBySid(Long sid);

    // 테스트에서 Member와 Review를 한번에 가져와야 하는데 Review 엔티티의 Member가 이미 fetch = FetchType.LAZY로 설정되어있음
    // @Transactional을 붙이면 Member의 Email을 처리할때마다 객체를 불러와야함(N+1문제 발생)
    // 해결방법은 @Query를 이용하거나 @EntityGraph를 사용
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<ShopReply> findByShop(Shop shop);

    @Modifying
    @Query("delete from ShopReply sr where sr.member = :member ")
    void deleteByMember(Member member);
}
