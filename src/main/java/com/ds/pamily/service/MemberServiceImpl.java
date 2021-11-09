package com.ds.pamily.service;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Long register(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        log.info("===<pamilyMember>=================");
        log.info(member);

        memberRepository.save(member);
        return member.getMid();
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Long mid = memberDTO.getMid();
        Optional<Member> result = memberRepository.findById(mid);
        if (result.isPresent()) {
            Member member = result.get();
            member.changeName(memberDTO.getName());
            member.changePassword(memberDTO.getPassword());
            memberRepository.save(member);
        }
    }

    @Override
    public void remove(Long mid) {
        memberRepository.deleteById(mid);
    }
}
