package tw.com.lyls.AppleJuice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 設定請求授權規則
        // @formatter:off
        http
            .authorizeRequests() // 設定授權請求規則
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // 設定 /swagger-ui/** 和 /v3/api-docs/** 路徑不需要驗證
                .antMatchers("/api/v1/**").permitAll() // 設定 /api/v1/** 路徑不需要驗證
                .antMatchers("/api/v2/**").authenticated(); // 設定 /api/v2/** 路徑需要驗證
        // @formatter:on

        // 返回配置的 HttpSecurity 物件
        return http.build();
    }
}