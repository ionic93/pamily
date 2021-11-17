package com.ds.pamily.service;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.MemberRole;
import com.ds.pamily.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member add(Member member) throws Exception {
        return memberRepository.save(member);
    }

    @Override
    public Long register(MemberDTO pamilyMemberDTO) {
        Member member = dtoToEntity(pamilyMemberDTO);
        if (get(member.getEmail()) == null) {
            try {
                member.addMemberRole(MemberRole.USER);
                add(member);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return 0L;
        }
        log.info("===<pamilyMember>=================");
        log.info(member);
        return member.getMid();
    }

    @Override
    public MemberDTO get(String email) {
        Optional<Member> result = memberRepository.findByEmail(email,false);
        if (result.isPresent()) {
            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Long mid = memberDTO.getMid();
        log.info("mid: "+mid);
        Optional<Member> result = memberRepository.findById(mid);
        if (result.isPresent()) {
            Member member = result.get();
            member.changeEmail(memberDTO.getEmail());
            member.changeName(memberDTO.getName());
            member.changePassword(memberDTO.getPassword());
            member.changeMobile(memberDTO.getMobile());
            memberRepository.save(member);
        }
    }
    @Override
    public void modifyNpass(MemberDTO memberDTO) {
        Long mid = memberDTO.getMid();
        log.info("mid: "+mid);
        Optional<Member> result = memberRepository.findById(mid);
        if (result.isPresent()) {
            Member member = result.get();
            member.changePassword(memberDTO.getPassword());
            memberRepository.save(member);
        }
    }

    @Override
    public void remove(Long mid) {
        memberRepository.deleteById(mid);
    }
}
