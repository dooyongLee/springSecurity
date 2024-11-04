package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 권한 설정 메소드
         * permitAll() : 모든 사용자 접근 허용
         * hasRole(String role) : 특정 역할 필요
         * hasAnyRole(String... roles) : 여러 역할 중 하나 필요
         * hasAuthority(String authority) : 특정 권한 필요
         * hasAnyAuthority(String...authorities) : 여러 권한 중 하나 필요
         * denyAll() : 모든 접근 거부
         * authenticated() : 인증된 사용자만 접근 가능
         * anonymous() : 익명 사용자만 접근 가능
         * rememberMe() : Remember-Me로 인증된 사용자 접근 가능
         */
        http.authorizeRequests((auth) -> auth
                .requestMatchers("/", "/login", "/join", "joinProc").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );

        http.formLogin((auth) -> auth.loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .permitAll()
        );

        /*csrf?
         * CSRF(Cross-Site Request Forgery)는 요청을 위조하여 사용자가 원하지 않아도 서버측으로 특정 요청을 강제로
         * 보내는 방식(ex:회원정보 변경, 게시글 CRUD)
         * 개발환경에서는 disable / 운영환경에서는 enable(CSRF필터 작동_토큰유무검증)
         * API서버의 경우 CSRF설정을 disable 로 처리하여 진행하여도 무관
         */

//        http.csrf((auth) -> auth.disable());
        /*
         *로그아웃 csrf 처리
         */
        http.logout((auth)-> auth.logoutUrl("/logout").logoutSuccessUrl("/"));

        /* session설정
         * sessionManagement()메소드를 통한 설정을 진행
         * maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
         * maxSessionPreventsLogin(불린) : 다중 로근인 개수를 초과하였을 경우 처리 방법
         * (true:초과시 새로운 로그인 차단 / false : 초과시 기존 세션 하나 삭제)
         */
        http.sessionManagement((auth) -> auth
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));


        /* session 고정 보호
         * sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
         * sessionManagement().sessionFixation().newSession(); : 로그인 시 세션 새로 생성
         * sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
         */

        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId());

        return http.build();
    }
}
