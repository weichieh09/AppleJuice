package tw.com.lyls.AppleJuice.filter;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {

    // 定義 traceId 的標頭名稱
    public static final String TRACE_ID_HEADER = "X-Trace-Id";

    /**
     * 執行過濾邏輯，為每個請求設置 traceId 並繼續處理過濾鏈。
     *
     * @param request     HTTP 請求
     * @param response    HTTP 回應
     * @param filterChain 過濾鏈
     * @throws ServletException Servlet 異常
     * @throws IOException      輸出異常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 使用 Hutool 的 IdUtil 生成唯一識別符
        String traceId = IdUtil.objectId();

        // 將 traceId 設置到 MDC 中，方便日誌追蹤
        MDC.put("traceId", traceId);

        try {
            // 繼續執行過濾鏈
            filterChain.doFilter(request, response);
        } finally {
            // 請求處理結束後，從 MDC 中移除 traceId，避免影響其他請求
            MDC.remove("traceId");
        }
    }
}
