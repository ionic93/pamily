package com.ds.pamily.dto;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopReplyDTO {
    private Long srid;

    private Long sid;

    private Long mid;

    //Member nickname;
    private String name;

    private String text;
    private LocalDateTime regDate, modDate;
}
