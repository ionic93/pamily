package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.entity.ShopImage;
import com.ds.pamily.repository.ShopCateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopCateSeviceImpl implements ShopCateService{
    private final ShopCateRepository shopCateRepository;

    @Override
    public List<ShopCateDTO> getCateList() {
        List<ShopCate> shopCateList = shopCateRepository.findAll();
        List<ShopCateDTO> shopCateDTOList = new ArrayList<>();

        for (ShopCate shopCate : shopCateList) {
            ShopCateDTO shopCateDTO = entityToDTO(shopCate);
            shopCateDTOList.add(shopCateDTO);
        }
        return shopCateDTOList;
    }
}
