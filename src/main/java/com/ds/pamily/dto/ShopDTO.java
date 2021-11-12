package com.ds.pamily.dto;

import com.ds.pamily.entity.ShopImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopDTO {
    private Long sid;
    private String title;
    private String content;

    private Long mid;
    private String name; //member nickname

    private String cateName;

    @Builder.Default
    //기본값
    private List<ShopImageDTO> shopImageDTOList = new ArrayList<>(); //게시글의 등록된 이미지들

    private int shopReplyCnt; //댓글 수

    private LocalDateTime regDate, modDate;
}
