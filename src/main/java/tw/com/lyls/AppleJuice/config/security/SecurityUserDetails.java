package tw.com.lyls.AppleJuice.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tw.com.lyls.AppleJuice.domain.mysql.User;

import java.util.Collection;

/**
 * Spring Security 需要的使用者資訊。
 *
 * 將查詢出來的其他資訊（例如 email、phone、status 等）放入 Spring Security 的 UserDetails，以便後續存取。
 * 例如，你可以建立自訂的 UserDetails 類別來擴充 User 資訊。
 */
@AllArgsConstructor
public class SecurityUserDetails implements UserDetails {

    @Getter
    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
//        如果你的系統有帳號有效期限（例如 VIP 會員 30 天內有效），可以用這個方法來檢查：
//        return LocalDate.now().isBefore(user.getExpireDate());
        return true; // true = 帳號未過期
    }

    @Override
    public boolean isAccountNonLocked() {
//        如果你的系統有多次登入失敗鎖定機制，可這樣設定：
//        return user.getFailedLoginAttempts() < 5; // 連續錯誤 5 次就鎖定
        return true; // true = 帳號未鎖定
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        如果你的系統要求使用者定期更換密碼（例如 90 天內必須更新），可以這樣做：
//        return user.getPasswordLastUpdated().plusDays(90).isAfter(LocalDate.now());
        return true; // true = 密碼未過期
    }

    @Override
    public boolean isEnabled() {
//        如果你的系統允許管理員手動啟用/停用帳號：
//        return user.getStatus() == 1; // 假設 status = 1 表示帳號啟用
        return true; // true = 帳號啟用
    }
}