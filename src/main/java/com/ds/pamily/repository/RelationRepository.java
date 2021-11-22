package com.ds.pamily.repository;

import com.ds.pamily.entity.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RelationRepository extends JpaRepository<Relation, Long> {
//    @Query("select r.following, r.name from Relation r group by r ")
//    Page<Object[]> getFollowList(Pageable pageable);
}
