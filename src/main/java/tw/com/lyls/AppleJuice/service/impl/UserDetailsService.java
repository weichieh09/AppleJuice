package tw.com.lyls.AppleJuice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.com.lyls.AppleJuice.domain.mysql.User;
import tw.com.lyls.AppleJuice.repository.mysql.UserRepository;

import java.util.Collections;

@Slf4j
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 透過 Spring Data JPA repository 依 username 查詢用戶
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 將查詢到的用戶資料轉換成 Spring Security 使用的 UserDetails
        // 此處僅設置空的權限列表，實際應用中可以依據需要設定相應的角色或權限
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList());
    }
}