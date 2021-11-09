package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email = :email and m.fromSocial = :social ")
    Optional<Member> findByEmail(String email, boolean social);


}
