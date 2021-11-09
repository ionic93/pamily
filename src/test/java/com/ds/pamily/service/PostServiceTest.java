package com.ds.pamily.service;

import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.PostImage;
import com.ds.pamily.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    protected PostRepository postRepository;


}