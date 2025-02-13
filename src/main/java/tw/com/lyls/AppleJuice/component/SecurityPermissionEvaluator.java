package tw.com.lyls.AppleJuice.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import tw.com.lyls.AppleJuice.repository.mysql.VwPermissionRoleRepository;

import java.io.Serializable;

@Slf4j
@Component
public class SecurityPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private VwPermissionRoleRepository vwAuthPermissionRepository;

    /**
     * 參數說明：
     * - targetDomainObject：我們在 @PreAuthorize 中傳入的資源名稱（例如："PRODUCT"）
     * - permission：我們傳入的操作類型（例如："EDIT"）
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || permission == null || targetDomainObject == null) {
            return false;
        }

        String resource = targetDomainObject.toString();
        String operation = permission.toString();

        // 檢查當前使用者所有角色是否有對應資源與操作的權限
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String role = grantedAuthority.getAuthority();  // 例如 "ROLE_USER" 或 "ROLE_ADMIN"
            boolean hasPerm = vwAuthPermissionRepository.existsByRoleNameAndPermissionResourceAndPermissionOperation(role, resource, operation);
            if (hasPerm) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // 針對不需要具體物件的情況，以 targetType 當作資源名稱處理
        if (targetType == null) {
            return false;
        }
        return hasPermission(authentication, targetType, permission);
    }

}
