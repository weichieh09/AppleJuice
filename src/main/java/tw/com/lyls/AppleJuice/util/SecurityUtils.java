package tw.com.lyls.AppleJuice.util;

import cn.hutool.core.util.ObjUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.com.lyls.AppleJuice.config.security.SecurityUserDetails;
import tw.com.lyls.AppleJuice.domain.mysql.User;

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

    private static SecurityUserDetails getSecurityUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (ObjUtil.isNotNull(authentication) &&
                authentication.getPrincipal() instanceof SecurityUserDetails) {
            return (SecurityUserDetails) authentication.getPrincipal();
        }
        return null;
    }
}