package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertPostReplys(){
        IntStream.rangeClosed(1,10).forEach(i->{
            Long mid = (long)(Math.random()*10)+1;
            //영화 번호
            Long pid = (long)(Math.random()*10)+1;

            String email = "user"+((long)(Math.random()*10)+1)+"@ds.com";

            Member member = Member.builder().mid(mid).build();

            Reply postReply = Reply.builder()
                    .member(member)
                    .post(Post.builder().pid(pid).build())
                    .text("이 게시글에 대한 느낌..."+i)
                    .build();
            replyRepository.save(postReply);
        });
    }
}