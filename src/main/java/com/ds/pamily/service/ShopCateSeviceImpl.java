package com.ds.pamily.service;

import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.repository.ShopCateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopCateSeviceImpl implements ShopCateService{

    private final ShopCateRepository shopCateRepository;

    @Override
    public List<ShopCateDTO> getCate() {
        List<ShopCate> result = shopCateRepository.findAll();
        return result.stream().map(shopCate -> entityToDTO(shopCate)).collect(Collectors.toList());

    }
}
