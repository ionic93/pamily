package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.ShopCate;

import java.util.List;

public interface ShopCateService {
    public List<ShopCateDTO> getCateList();

    default ShopCate dtoToEntity(ShopCateDTO shopCateDTO) {
        ShopCate shopCate = ShopCate.builder()
                .scno(shopCateDTO.getScno())
                .cateName(shopCateDTO.getCateName())
                .build();
        return shopCate;
    }

    default ShopCateDTO entityToDTO(ShopCate shopCate) {
        ShopCateDTO shopCateDTO = ShopCateDTO.builder()
                .scno(shopCate.getScno())
                .cateName(shopCate.getCateName())
                .build();
        return shopCateDTO;
    }
}
