package com.ds.pamily.service;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.entity.Member;

import java.util.List;

public interface MemberService {
    Long register(MemberDTO memberDTO);

    void modify(MemberDTO memberDTO);
    void remove(Long mid);

    default Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
                .mid(memberDTO.getMid())
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .fromSocial(memberDTO.isFromSocial())
                .build();
        return member;
    }

    default MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .fromSocial(member.isFromSocial())
                .build();
        return memberDTO;
    }
}
