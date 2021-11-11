package com.ds.pamily.service;

import com.ds.pamily.dto.ItemCateDTO;
import com.ds.pamily.dto.ItemDTO;
import com.ds.pamily.dto.ItemTypeDTO;
import com.ds.pamily.entity.Item;
import com.ds.pamily.entity.ItemCate;
import com.ds.pamily.entity.ItemType;
import com.ds.pamily.repository.ItemCateRepository;
import com.ds.pamily.repository.ItemRepository;
import com.ds.pamily.repository.ItemTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ItemTypeRepository itemTypeRepository;
    private final ItemCateRepository itemCateRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemTypeDTO> getType() {
        List<ItemType> result = itemTypeRepository.findAll();
        return result.stream().map(itemType -> typeEntityToDTO(itemType)).collect(Collectors.toList());
    }

    @Override
    public List<ItemCateDTO> getCate() {
        List<ItemCate> result = itemCateRepository.findAll();
        return result.stream().map(itemCate -> cateEntityToDTO(itemCate)).collect(Collectors.toList());
    }

//    @Override
//    public ItemTypeDTO getType() {
//        ItemTypeDTO itemTypeDTO = new ItemTypeDTO();
//        List<ItemType> itemTypes = itemTypeRepository.findAll();
//        return itemTypeDTO;
//    }

//    @Override
//    public List<ItemCateDTO> getCate() {
//        List<ItemCateDTO> cateList = itemCateRepository.findAll();
//        return cateList;
//    }

    @Transactional
    @Override
    public ItemDTO getItem() {
        ItemDTO itemDTO = new ItemDTO();
        List<ItemCate> itemCates = itemCateRepository.findAll();
        itemDTO.setItemCates(itemCates);
        HashMap<Long, List<Item>> map = new HashMap<>();
        itemDTO.setItems(map);

        for (int i = 0; i < itemCates.size(); i++) {
            ItemCate itemCate = itemCates.get(i);
            Long icno = itemCate.getIcno();
            List<Item> list = itemRepository.findByItemCate(itemCate);
            HashMap<Long, List<Item>> forMap = itemDTO.getItems();
            forMap.put(icno, list);
            log.info("itemDTO >>>>> " + itemDTO);
        }
        return itemDTO;
    }
}
