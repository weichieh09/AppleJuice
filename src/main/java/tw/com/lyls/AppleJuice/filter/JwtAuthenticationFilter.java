package tw.com.lyls.AppleJuice.filter;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.com.lyls.AppleJuice.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. 從 HTTP Header 中取得 Authorization 欄位內容
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. 檢查 header 是否不為空且以 "Bearer " 開頭
        if (StrUtil.isNotBlank(header) && StrUtil.startWith(header, "Bearer ")) {
            // 從 header 中去除 "Bearer " 前綴，取出 JWT token 字串
            token = StrUtil.subSuf(header, 7);

            // 3. 使用自定義工具驗證 token 是否有效（例如簽名正確、未過期）
            if (jwtService.validateToken(token)) {
                // 4. 若 token 有效，從 token 解析出用戶名
                username = jwtService.extractUsername(token);
            }
        }

        // 5. 若成功從 token 中取得 username 且目前安全上下文中尚未設置認證資訊
        if (StrUtil.isNotBlank(username) &&
                ObjUtil.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            // 6. 根據 username 從資料庫中取得該用戶的詳細資訊
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 7. 根據取得的 UserDetails 建立一個認證 token
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 8. 設置其他認證相關資訊（例如來源 IP 等）
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 9. 將認證資訊放入 Spring Security 的上下文中，代表該請求已通過認證
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 10. 繼續執行過濾器鏈中的下一個過濾器或處理請求
        filterChain.doFilter(request, response);
    }
}
