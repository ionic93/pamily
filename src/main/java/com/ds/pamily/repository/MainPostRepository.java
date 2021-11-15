package com.ds.pamily.repository;

import com.ds.pamily.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainPostRepository extends JpaRepository<Post, Long> {

    @Query("select p, pi, count(distinct r) from Post p left outer join PostImage pi on pi.post = p " +
            " left outer join Reply r on r.post = p group by p ")
    Page<Object[]> getMainPage(Pageable pageable);

//    @Query("select p, pi, count(r) " +
//            "from Post p left outer join PostImage pi on pi.post = p " +
//            "left outer join Reply r on r.post = p " +
//            "where p.pid = :pid group by pi ")
//    List<Object[]> getPostWithAll(Long pid); //특정 영화 조회


}
