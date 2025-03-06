package tw.com.lyls.AppleJuice.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.com.lyls.AppleJuice.config.security.SecurityUserDetails;
import tw.com.lyls.AppleJuice.domain.mysql.User;

import java.util.List;

/**
 * Spring Security 的工具類別。
 */
public final class SecurityUtils {

    /**
     * 取得當前登入的使用者。
     *
     * @return 使用者物件
     */
    public static User getCurrentUser() {
        SecurityUserDetails securityUserDetails = getSecurityUserDetails();
        return ObjUtil.isNull(securityUserDetails) ? null : securityUserDetails.getUser();
    }

    /**
     * 取得當前登入的使用者的角色清單。
     *
     * @return 角色清單
     */
    public static List<String> getCurrentUserRoles() {
        List<String> result = CollUtil.newArrayList();
        Authentication authentication = getAuthentication();
        if (ObjUtil.isNotNull(authentication)) {
            authentication.getAuthorities().forEach(grantedAuthority -> result.add(grantedAuthority.getAuthority()));
        }
        return result;
    }

    private static SecurityUserDetails getSecurityUserDetails() {
        Authentication authentication = getAuthentication();
        if (ObjUtil.isNotNull(authentication) &&
                authentication.getPrincipal() instanceof SecurityUserDetails) {
            return (SecurityUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    private static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }
}