package com.ds.pamily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCateDTO {
   private Long icno;
   private String cateName;
   private Long itno;
}
