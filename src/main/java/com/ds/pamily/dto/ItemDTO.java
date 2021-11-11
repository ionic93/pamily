package com.ds.pamily.dto;

import com.ds.pamily.entity.Item;
import com.ds.pamily.entity.ItemCate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private List<ItemCate> itemCates;
    private HashMap<Long, List<Item>> items;
}
