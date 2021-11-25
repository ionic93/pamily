package com.ds.pamily.repository;

import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void insertAdmin() {
        Member member = Member.builder()
                .email("admin@ds.com")
                .password(passwordEncoder.encode("123456"))
                .name("admin")
                .mobile("010-1112-4465")
                .fromSocial(false)
                .build();
        member.addMemberRole(MemberRole.USER);
        member.addMemberRole(MemberRole.MANAGER);
        member.addMemberRole(MemberRole.ADMIN);

        memberRepository.save(member);
    }
    @Test
    public void insertUser() {
        Member member = Member.builder()
                .email("testuser@ds.com")
                .password(passwordEncoder.encode("1"))
                .name("test")
                .mobile("01011111111")
                .fromSocial(false)
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
    }
    @Test
    public void insertManager() {
        Member member = Member.builder()
                .email("testmanager@ds.com")
                .password(passwordEncoder.encode("1"))
                .mobile("01011111111")
                .name("manager")
                .fromSocial(false)
                .build();
        member.addMemberRole(MemberRole.USER);
        member.addMemberRole(MemberRole.MANAGER);
        memberRepository.save(member);
    }

    @Test
    public void insertDummies() {
        // 1~7 까지는 USER만 지정
        // 8~9 까지는 USER,MANGER 지정
        // 9~10 까지는 USER,MANGER,ADMIN 지정
        IntStream.rangeClosed(1,30).forEach(i->{
            int num = (int)(Math.random()*9);
            int num2 = (int)(Math.random()*9);
            int num3 = (int)(Math.random()*9);
            int num4 = (int)(Math.random()*9);
            Member member = Member.builder()
                    .email("user"+i+"@ds.com")
                    .name("user"+i)
                    .mobile("010-"+num+num2+num3+num4+"-"+num+num2+num3+num4)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("123456"))
                    .build();

            //default role
            member.addMemberRole(MemberRole.USER);
            if (i > 27) {
                member.addMemberRole(MemberRole.MANAGER);
            }
            if (i > 29) {
                member.addMemberRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

}