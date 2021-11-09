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
public class MemberDTO {
    private Long mid;
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    private LocalDateTime regDate, modDate;
}
