package com.ds.pamily.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/member/login")
                .loginProcessingUrl("/login")
//                .successHandler(successHandler())
                .failureUrl("/member/login?error");
        http.csrf().disable();
        http.logout();
//        http.oauth2Login().successHandler(successHandler());
//        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
//
//        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().sameOrigin();

    }

//    @Bean
//    public ClubLoginSuccessHandler successHandler() {
//        return new ClubLoginSuccessHandler(passwordEncoder());
//    }

//    @Bean
//    public ApiLoginFilter apiLoginFilter() throws Exception{
//        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login",jwtUtil());
//        apiLoginFilter.setAuthenticationManager(authenticationManager());
//
//        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
//        return apiLoginFilter;
//    }
//
//    @Bean
//    public ApiCheckFilter apiCheckFilter() {
//        return new ApiCheckFilter("/notes/**/*", jwtUtil());
//    }
//
//    @Bean
//    public JWTUtil jwtUtil() {
//        return new JWTUtil();
//    }
}