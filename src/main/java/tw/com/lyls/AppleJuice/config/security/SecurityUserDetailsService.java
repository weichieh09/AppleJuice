package tw.com.lyls.AppleJuice.config.security;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.com.lyls.AppleJuice.domain.mysql.User;
import tw.com.lyls.AppleJuice.domain.mysql.VwRoleUser;
import tw.com.lyls.AppleJuice.repository.mysql.UserRepository;
import tw.com.lyls.AppleJuice.repository.mysql.VwRoleUserRepository;

import java.util.List;

@Slf4j
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VwRoleUserRepository vwRoleUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查用戶
        User user = userRepository.findByUsername(username);
        if (ObjUtil.isNull(user)) {
            throw new UsernameNotFoundException(StrUtil.format("username：{}，不存在！", username));
        }

        // 查腳色
        List<VwRoleUser> vwRoleUserList = vwRoleUserRepository.findByUserId(user.getId());
        List<String> authRoleList = CollStreamUtil.toList(vwRoleUserList, VwRoleUser::getRoleName);
        List<SimpleGrantedAuthority> authorities = CollStreamUtil.toList(authRoleList, SimpleGrantedAuthority::new);

        // 回傳自訂的 UserDetails
        return new SecurityUserDetails(user, authorities);
    }
}