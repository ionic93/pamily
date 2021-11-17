package com.ds.pamily.service;

import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.repository.ShopCateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShopCateServiceTest {
    @Autowired
    private ShopCateRepository shopCateRepository;
    @Autowired
    private ShopCateService shopCateService;

    @Test
    public void testCategory() {
        List<ShopCate> shopCateList = shopCateRepository.findAll();
        List<ShopCateDTO> shopCateDTOList = new ArrayList<>();

        for (ShopCate shopCate : shopCateList) {
            ShopCateDTO shopCateDTO = shopCateService.entityToDTO(shopCate);
            shopCateDTOList.add(shopCateDTO);
        }
        System.out.println(shopCateDTOList);
    }
}