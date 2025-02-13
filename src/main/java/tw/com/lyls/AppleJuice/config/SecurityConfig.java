package tw.com.lyls.AppleJuice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.com.lyls.AppleJuice.component.SecurityAuthenticationEntryPoint;
import tw.com.lyls.AppleJuice.filter.JwtAuthenticationFilter;
import tw.com.lyls.AppleJuice.filter.TraceIdFilter;
import tw.com.lyls.AppleJuice.service.impl.SecurityUserDetailsService;

/**
 * 安全性配置類別，負責設定應用程式的安全性規則。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    /**
     * 配置安全性過濾鏈 (SecurityFilterChain)。
     * 此配置中除了 JWT 認證外，也加入了 TraceIdFilter，
     * 使每個請求都會先被賦予 traceId，再進行後續的安全檢查。
     *
     * @param http HttpSecurity 物件，用於配置安全性規則
     * @return 配置後的 SecurityFilterChain 物件
     * @throws Exception 可能拋出的異常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 設定請求授權規則
        // @formatter:off
        http
                .csrf().disable() // 關閉 CSRF 防護
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger UI 與 API Docs 不需要驗證
                .antMatchers("/api/v1/**").permitAll() // API v1 的端點不需要驗證
                .anyRequest().authenticated();

        // 在 UsernamePasswordAuthenticationFilter 前依序加入 TraceIdFilter 與 JWT 檢查過濾器
        // TraceIdFilter 會在請求到達 Controller 前先賦予 traceId
        http
                .addFilterBefore(traceIdFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint);
        // @formatter:on

        // 返回配置後的 HttpSecurity 物件
        return http.build();
    }

    /**
     * 註冊 DaoAuthenticationProvider，並指定 UserDetailsService 與密碼編碼器。
     *
     * @return DaoAuthenticationProvider 物件
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 配置 AuthenticationManager，用於身份驗證。
     *
     * @param authConfig AuthenticationConfiguration 物件
     * @return AuthenticationManager 物件
     * @throws Exception 可能拋出的異常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 定義密碼編碼器，此處使用 NoOpPasswordEncoder（不進行加密），
     * 實際專案中建議使用更安全的編碼器。
     *
     * @return PasswordEncoder 物件
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 註冊 TraceIdFilter 的 Bean。
     * 此過濾器會為每個 HTTP 請求生成或提取 traceId，並將其放入 MDC 中，
     * 方便跨層級的日誌追蹤。
     *
     * @return TraceIdFilter 物件
     */
    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }
}