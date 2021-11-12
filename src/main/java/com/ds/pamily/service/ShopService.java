package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.dto.ShopImageDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.entity.ShopImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ShopService {
    Long shopRegister(ShopDTO shopDTO);

    PageResultDTO<ShopDTO, Object[]> getList(PageRequestDTO requestDTO); //목록처리

    default ShopDTO entitiesToDTO(Shop shop, List<ShopImage> shopImages, Long shopReplyCnt ) {
        ShopDTO shopDTO = ShopDTO.builder()
                .sid(shop.getSid())
                .title(shop.getTitle())
                .content(shop.getContent())
                .mid(shop.getMember().getMid())
                .name(shop.getMember().getName())
                .scno(shop.getScno().getScno())
                .cateName(shop.getScno().getCateName())
                .regDate(shop.getRegDate())
                .modDate(shop.getModDate())
                .build();

        List<ShopImageDTO> shopImageDTOList = shopImages.stream().map(shopImage -> {
            return ShopImageDTO.builder().imgName(shopImage.getImgName())
                    .path(shopImage.getPath())
                    .uuid(shopImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        shopDTO.setShopImageDTOList(shopImageDTOList);
        shopDTO.setShopReplyCnt(shopReplyCnt.intValue());

        return shopDTO;
    }

    default Map<String, Object> dtoToEntity(ShopDTO shopDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Shop shop = Shop.builder()
                .sid(shopDTO.getSid())
                .title(shopDTO.getTitle())
                .content(shopDTO.getContent())
                .member(Member.builder().mid(shopDTO.getMid()).build())
                .scno(ShopCate.builder().scno(shopDTO.getScno()).build())
                .build();

        entityMap.put("shop",shop);
        List<ShopImageDTO> shopImageDTOList = shopDTO.getShopImageDTOList();

        if (shopImageDTOList != null && shopImageDTOList.size() > 0) {
            List<ShopImage> shopImageList =shopImageDTOList.stream()
                    .map(shopImageDTO -> {
                        ShopImage shopImage = ShopImage.builder()
                                .path(shopImageDTO.getPath())
                                .imgName(shopImageDTO.getImgName())
                                .uuid(shopImageDTO.getUuid())
                                .shop(shop)
                                .build();
                        return shopImage;
                    }).collect(Collectors.toList());
            entityMap.put("shopImgList", shopImageList);
        }
        return entityMap;
    }
}
