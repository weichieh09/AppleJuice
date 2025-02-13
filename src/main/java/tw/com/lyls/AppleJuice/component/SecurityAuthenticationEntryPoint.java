package tw.com.lyls.AppleJuice.component;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;
import tw.com.lyls.AppleJuice.service.vm.ErrorRespVM;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 當未授權的請求被攔截時，此方法會被調用。
     * 主要負責處理 401 未授權錯誤，並返回標準的 JSON 錯誤響應。
     *
     * @param request       當前的 HTTP 請求
     * @param response      HTTP 回應對象
     * @param authException 認證異常資訊
     * @throws IOException      可能發生的輸出例外
     * @throws ServletException 可能發生的 Servlet 例外
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 設置 HTTP 回應狀態碼為 401 未授權
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);

        // 設置回應內容類型為 JSON，並使用 UTF-8 避免中文亂碼
        response.setContentType("application/json;charset=UTF-8");

        // 回應錯誤訊息，封裝成標準錯誤格式的 JSON
        response.getWriter().write(
                JSONUtil.toJsonStr(new ErrorRespVM(ErrorEnum.HTTP_UNAUTHORIZED))
        );

        // 記錄未授權存取的日誌
        log.warn("未授權的存取請求，請求 URI：{}", request.getRequestURI());
    }
}