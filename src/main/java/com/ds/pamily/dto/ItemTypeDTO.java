package com.ds.pamily.dto;

import com.ds.pamily.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemTypeDTO {
    private Long itno;
    private String typeName;
}
