package tw.com.lyls.AppleJuice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import tw.com.lyls.AppleJuice.component.CustomPermissionEvaluator;

/**
 * 方法級別的安全性配置類，用於啟用 Spring Security 的方法安全控制。
 * <p>
 * 這個類使用 @EnableGlobalMethodSecurity(prePostEnabled = true) 來啟用 @PreAuthorize、@PostAuthorize、@PreFilter 和 @PostFilter 註解，
 * 允許在方法級別進行權限驗證。
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * 自訂的權限評估器 (PermissionEvaluator)，用於擴展 Spring Security 的權限驗證邏輯。
     */
    @Autowired
    private CustomPermissionEvaluator customPermissionEvaluator;

    /**
     * 設定方法安全性表達式處理器 (MethodSecurityExpressionHandler)，
     * 並將自訂的權限評估器 (customPermissionEvaluator) 設置進入處理器中，
     * 讓方法級別的安全性檢查可以使用自訂的權限驗證邏輯。
     *
     * @return 方法安全性表達式處理器
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
    }

}