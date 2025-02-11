package tw.com.lyls.AppleJuice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Spring Security 的工具類別。
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * 取得當前使用者的登入名稱。
     *
     * @return 當前使用者的登入名稱。
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 取得當前使用者的 JWT。
     *
     * @return 當前使用者的 JWT。
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
                .ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * 檢查使用者是否已驗證身份。
     *
     * @return 如果使用者已驗證身份則回傳 true，否則回傳 false。
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities(authentication).noneMatch("ROLE_ANONYMOUS"::equals);
    }

    /**
     * 檢查當前使用者是否擁有任一指定的權限。
     *
     * @param authorities 欲檢查的權限。
     * @return 如果當前使用者擁有任一權限則回傳 true，否則回傳 false。
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
                authentication != null && getAuthorities(authentication).anyMatch(authority -> Arrays.asList(authorities).contains(authority))
        );
    }

    /**
     * 檢查當前使用者是否不具備任何指定的權限。
     *
     * @param authorities 欲檢查的權限。
     * @return 如果當前使用者不具備任何指定的權限則回傳 true，否則回傳 false。
     */
    public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
        return !hasCurrentUserAnyOfAuthorities(authorities);
    }

    /**
     * 檢查當前使用者是否擁有特定的權限。
     *
     * @param authority 欲檢查的權限。
     * @return 如果當前使用者擁有該權限則回傳 true，否則回傳 false。
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
}