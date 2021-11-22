package com.ds.pamily.security.service;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.MemberRole;
import com.ds.pamily.repository.MemberRepository;
import com.ds.pamily.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("---------------------------------");
        log.info("userRequest: "+userRequest); //org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest객체

        String clientName = userRequest.getClientRegistration().getClientName(); //Google
        log.info("clientName: "+clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("=======================");

        oAuth2User.getAttributes().forEach((k,v)->{ //sub, email, email_verfied, EMAIL등이 출력
            log.info(k+":"+v);
        });

        String email = null;
        if (clientName.endsWith("Google")) {//구글을 이용하는 경우
            email = oAuth2User.getAttribute("email");
        }
        log.info("EMAIL: "+email);

//        ClubMember member = saveSocialMember(email);
//        return oAuth2User;

        Member member = saveSocialMember(email);
        
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream()
                        .map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMemberDTO.setMid(member.getMid());
        authMemberDTO.setName(member.getName());
        return authMemberDTO;
    }
    //email을 db에 검색, 있으면 불러오고 없으면 생성함
    private Member saveSocialMember(String email) {
        Optional<Member> result = repository.findByEmail(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.USER);
        repository.save(member);
        return member;
    }
}
