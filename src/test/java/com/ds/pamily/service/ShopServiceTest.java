package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.repository.ShopImageRepository;
import com.ds.pamily.repository.ShopReplyRepository;
import com.ds.pamily.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopServiceTest {
    @Autowired
    private  ShopService shopService;
    @Autowired
    private  ShopReplyRepository shopReplyRepository;
    @Autowired
    private  ShopImageRepository shopImageRepository;

    @Test
    public void testshopremove() {
//        Long sid =
    }

}