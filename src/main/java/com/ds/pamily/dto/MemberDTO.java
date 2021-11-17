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
    private String mobile;
    private boolean fromSocial;

    private LocalDateTime regDate, modDate;

    public boolean equals(MemberDTO memberDTO) {
        String email = this.email;
        String password = this.password;
        String name = this.name;
        String mobile = this.mobile;

        boolean eq = memberDTO.email.equals(email) && memberDTO.password.equals(password) && memberDTO.name.equals(name) && memberDTO.mobile.equals(mobile);
        return eq;
    }

}
