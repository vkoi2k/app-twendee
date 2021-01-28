package com.twendee.app.service.impl;

import com.twendee.app.model.entity.CustomUserDetail;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public  UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User currentUser = userRepository.getUserByEmail(email);
        String currentRole;
        if (currentUser == null) {
            throw new UsernameNotFoundException(email);
        }
        Boolean roleUser = currentUser.isRole();
        if(roleUser == true){
             currentRole = "ADMIN";

        }
        else {
            currentRole = "USER";

        }
        currentRole = "ROLE_".concat(currentRole);
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(currentRole);

        CustomUserDetail customUserDetail = new CustomUserDetail(currentUser, authorities);
        System.out.println(customUserDetail);
        return customUserDetail;
    }
}
