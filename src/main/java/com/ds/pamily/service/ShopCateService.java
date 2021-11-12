package com.ds.pamily.service;

import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.entity.ShopCate;

import java.util.List;

public interface ShopCateService {
    public List<ShopCateDTO> getCate();


    default ShopCate dtoToEntity(ShopCateDTO cateDTO) {
        ShopCate shopCate = ShopCate.builder()
                .scno(cateDTO.getScno())
                .cateName(cateDTO.getCateName())
                .build();
        return shopCate;
    }

    default ShopCateDTO entityToDTO(ShopCate shopCate) {
        ShopCateDTO cateDTO = ShopCateDTO.builder()
                .scno(shopCate.getScno())
                .cateName(shopCate.getCateName())
                .build();
        return cateDTO;
    }
}
