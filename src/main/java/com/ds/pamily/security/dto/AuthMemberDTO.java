package com.ds.pamily.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
//이 클래스는 보안과 인증만을 위해 만든 클래스
public class AuthMemberDTO extends User implements OAuth2User {
    private Long mid;
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr; //구글로부터 받아오는 정보 (구글 id, picture 등 )

    public AuthMemberDTO(String username,
                         String password,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
       this(username, password, fromSocial, authorities);
       this.attr = attr;
    }

    // 부모인 User 클래스의 생성자를 호출하는 메서드
    public AuthMemberDTO(String username,
                         String password,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial =  fromSocial;
        //즉 DTO의 역할과 동시에 스프링 시큐리티에서 인가/인증 작업에 사용가능
        //password는 부모 클래스를 사용하므로 별도로 선언하지 않음
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
