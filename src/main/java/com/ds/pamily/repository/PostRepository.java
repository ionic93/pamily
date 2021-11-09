package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p, pi, count(distinct r) from Post p left outer join PostImage pi on pi.post = p " +
            " left outer join Reply r on r.post = p group by p ")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select p, pi, count(r) " +
            "from Post p left outer join PostImage pi on pi.post = p " +
            "left outer join Reply r on r.post = p " +
            "where p.pid = :pid group by pi ")
    List<Object[]> getPostWithAll(Long pid); //특정 영화 조회

//    //메인화면에서 내가 팔로잉한 유저의 게시물 보여주기
//    @Query(value = "SELECT * FROM post WHERE user_id IN (SELECT to_user_id FROM FOLLOW WHERE from_user_id = :sessionId) ORDER BY id DESC", nativeQuery = true)
//    Page<Post> mainStory(long sessionId, Pageable pageable);
}
