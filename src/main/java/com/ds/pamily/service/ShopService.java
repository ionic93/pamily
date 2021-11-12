package com.ds.pamily.service;

import com.ds.pamily.dto.*;
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
    public List<ShopDTO> getShop();

    PageResultDTO<ShopDTO, Shop> getShopList(PageRequestDTO dto);
    ShopDTO shopRead(Long gno);
    void shopRemove(Long gno);
    void shopModify(ShopDTO dto);

    default Map<String, Object> shopDtoToEntity(ShopDTO shopDTO) {
        Map<String, Object> shopEitityMap = new HashMap<>();
        Shop shop = Shop.builder()
                .sid(shopDTO.getSid())
                .title(shopDTO.getTitle())
                .content(shopDTO.getContent())
                .member(Member.builder().mid(shopDTO.getMid()).build())
                .build();
        shopEitityMap.put("shop", shop);

        List<ShopImageDTO> shopImageDTOList = shopDTO.getShopImageDTOList();
        if (shopImageDTOList != null && shopImageDTOList.size()>0) {
            List<ShopImage> shopImageList = shopImageDTOList.stream().map(shopImageDTO -> {
                ShopImage shopImage = ShopImage.builder()
                        .path(shopImageDTO.getPath())
                        .imgName(shopImageDTO.getImgName())
                        .uuid(shopImageDTO.getUuid())
                        .shop(shop)
                        .build();
                return shopImage;
            }).collect(Collectors.toList());
            shopEitityMap.put("imgList", shopImageList);
        }
        return shopEitityMap;

    }

    default ShopDTO shopEntityToDTO(Shop entity) {
        ShopDTO shopDTO = ShopDTO.builder()
                .sid(entity.getSid())
                .title(entity.getTitle())
                .content(entity.getContent())
                .name(entity.getMember().getName())
                .cateName(entity.getScno().getCateName())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return shopDTO;
    }

}
