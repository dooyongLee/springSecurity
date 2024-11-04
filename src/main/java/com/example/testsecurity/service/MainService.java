package com.example.testsecurity.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class MainService {
    public String checkName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(name.equals("anonymousUser")){
            return "로그인해주세요";
        }else{
            return name;
        }
    }

    public String checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        if(role.equals("ROLE_ANONYMOUS")){
            return "권한이 없습니다.";
        } else {
            return role;
        }
    }
}
