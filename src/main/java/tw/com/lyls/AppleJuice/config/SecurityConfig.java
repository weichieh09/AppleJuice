package tw.com.lyls.AppleJuice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 安全性配置類別，負責設定應用程式的安全性規則。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置安全性過濾鏈 (SecurityFilterChain)
     * @param http HttpSecurity 物件，用於配置安全性規則
     * @return 配置後的 SecurityFilterChain 物件
     * @throws Exception 可能拋出的異常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 設定請求授權規則
        // @formatter:off
        http
                .authorizeRequests() // 設定授權請求規則
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // 設定 Swagger UI 和 API Docs 的端點不需要驗證
                .antMatchers("/api/v1/**").permitAll() // 設定 /api/v1/** 路徑不需要驗證
                .antMatchers("/api/v2/**").authenticated(); // 設定 /api/v2/** 路徑需要驗證
        // @formatter:on

        // 返回配置的 HttpSecurity 物件
        return http.build();
    }
}
