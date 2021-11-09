package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.PostImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Commit
    @Transactional
    @Test
    public void insertPosts() {
        IntStream.rangeClosed(1,10).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;
            Member member = Member.builder().mid(mid).build();
            Post post = Post.builder().member(member).content("Contents..."+i).build();
            System.out.println("------------------------------");
            postRepository.save(post);

            //1~5를 랜덤하게 뽑음
            //밑의 for문과 연결하면 영화 이미지가 최소 1장에서 최대 3장까지 movie와 연결 가능
            int count = (int)(Math.random()*3)+1;

            for (int j = 0; j < count; j++) {
                PostImage postImage = PostImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .post(post)
                        .imgName("test"+j+".jpg")
                        .build();
                postImageRepository.save(postImage);
            }
            System.out.println("========================");
        });
    }

}