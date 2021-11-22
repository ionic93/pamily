package com.ds.pamily.repository;

import com.ds.pamily.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select profileId, pinum from Profile profileId left outer join ProfileImage pinum on pinum.profile = profileId group by profileId ")
    Page<Object[]> getListPage(Pageable pageable);

//    @Query("select p, pi, count(r) " +
//            "from Post p left outer join PostImage pi on pi.post = p " +
//            "left outer join Reply r on r.post = p " +
//            "where p.pid = :pid group by pi ")
//    List<Object[]> getPostWithAll(Long pid); //특정 영화 조회

}
