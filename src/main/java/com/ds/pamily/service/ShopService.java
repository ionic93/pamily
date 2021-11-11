package com.ds.pamily.service;

import com.ds.pamily.dto.ItemCateDTO;
import com.ds.pamily.dto.ItemDTO;
import com.ds.pamily.dto.ItemTypeDTO;
import com.ds.pamily.entity.ItemCate;
import com.ds.pamily.entity.ItemType;

import java.util.List;

public interface ShopService {
    public List<ItemTypeDTO> getType();

    public List<ItemCateDTO> getCate();

    public ItemDTO getItem();

    default ItemCate cateDTOToEntity(ItemCateDTO cateDTO) {
        ItemCate itemCate = ItemCate.builder()
                .icno(cateDTO.getIcno())
                .cateName(cateDTO.getCateName())
                .itemType(ItemType.builder().itno(cateDTO.getItno()).build())
                .build();
        return itemCate;
    }

    default ItemCateDTO cateEntityToDTO(ItemCate itemCate) {
        ItemCateDTO cateDTO = ItemCateDTO.builder()
                .icno(itemCate.getIcno())
                .cateName(itemCate.getCateName())
                .itno(itemCate.getItemType().getItno())
                .build();
        return cateDTO;
    }

    default ItemType typeDTOToEntity(ItemTypeDTO typeDTO) {
        ItemType itemType = ItemType.builder()
                .itno(typeDTO.getItno())
                .typeName(typeDTO.getTypeName())
                .build();
        return itemType;
    }

    default ItemTypeDTO typeEntityToDTO(ItemType itemType) {
        ItemTypeDTO typeDTO = ItemTypeDTO.builder()
                .itno(itemType.getItno())
                .typeName(itemType.getTypeName())
                .build();
        return typeDTO;
    }

}
