package com.ds.pamily.repository;

import com.ds.pamily.entity.ParmImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParmImageRepository extends JpaRepository<ParmImage, Long> {
//    @Query("select p, avg(coalesce(p.grade 0)), count(distinct) p) from Parm p " +
//            " left outer join ParmImage pi on pi.movie = p group by p ")
//    Page<Object[]> getListPage(Pageable pageable);

}
