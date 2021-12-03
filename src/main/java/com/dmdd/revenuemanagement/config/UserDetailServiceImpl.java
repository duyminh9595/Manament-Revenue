package com.dmdd.revenuemanagement.config;

import com.dmdd.revenuemanagement.dao.UserInfoRepository;
import com.dmdd.revenuemanagement.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo userInfo=userInfoRepository.findByEmail(email);
        if(userInfo==null)
        {
            throw new UsernameNotFoundException("Could not found email");
        }
        return new UserDetailImpl(userInfo);
    }
}
