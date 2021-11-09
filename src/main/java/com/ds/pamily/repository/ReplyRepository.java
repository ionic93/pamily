package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> { // 테스트에서 Member와 Review를 한번에 가져와야 하는데 Review 엔티티의 Member가 이미 fetch = FetchType.LAZY로 설정되어있음
    // @Transactional을 붙이면 Member의 Email을 처리할때마다 객체를 불러와야함(N+1문제 발생)
    // 해결방법은 @Query를 이용하거나 @EntityGraph를 사용
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByPost(Post post);
    // attributePaths = 로딩 설정을 변경하고 싶은 속성의 이름을 배열로 명시
    // type = @EntityGraph의 적용 방식 설정
    // FETCH = attributePaths에서 명시한 속성만 EAGER로 처리하고 나머지는 LAZY로 처리
    // LOAD = attributePaths에서 명시한 속성만 EAGER로 처리하고 나머지는 엔티티와 동일하게 처리

    @Modifying //update나 delete를 사용하려면 반드시 필요
    //@Query를 붙이지 않을면 Member랑 연관관계가 있던 Review가 지워질때 리뷰 개수만큼 쿼리가 반복된다.
    @Query("delete from Reply pr where pr.member = :member ")
    void deleteByMember(Member member);

}
