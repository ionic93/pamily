package com.ds.pamily.security.service;

import com.ds.pamily.entity.Member;
import com.ds.pamily.repository.MemberRepository;
import com.ds.pamily.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
//AuthenticationManager가 내부적으로 UserDetailsService를 호출해서 사용자의 정보를 가져옴
//이것을 JPA로 사용자의 정보를 가져오고 싶다면  UserDetailsService가 이용하는 구조로 작성할것
public class PamilyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    //UserDetailsService는 loadUserByUsername()이라는 단 하나의 메서드를 가지고 있음
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PamilyUserDetailsService loadUserByUsername "+ username);

        Optional<Member> result = memberRepository.findByEmail(username, false);
        if (!result.isPresent()){
            //Email이 존재 하지 않을경우 처리
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = result.get();
            log.info("--------------------------");
            log.info("Member: "+ member);

        //ClubMember를 UserDetails타입으로 처리하기 위해 DTO타입으로 변환
        //ClubMemberRole은 스프링 시큐리티에서 사용하는 SimpleGrantedAuthority로 변환
        //이때, "ROLE_"이라는 접두어를 추가 =>Enum에 있던 상수
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );
        authMemberDTO.setMid(member.getMid());
        authMemberDTO.setName(member.getName());
        authMemberDTO.setFromSocial(member.isFromSocial());

        return authMemberDTO;
    }
}
