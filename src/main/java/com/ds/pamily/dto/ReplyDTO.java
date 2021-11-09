package com.ds.pamily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    //reply id
    private Long rid;

    //Post id
    private Long pid;

    //Member id
    private Long mid;

    //Member nickname;
    private String name;

    //Member email
    private String email;

    private String text;
    private LocalDateTime regDate, modDate;
}
