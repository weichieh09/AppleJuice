package tw.com.lyls.AppleJuice.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * TraceIdFilter - 追蹤 ID 過濾器
 * <p>
 * 此過濾器的作用是為每個進入的 HTTP 請求生成或提取一個唯一的 traceId，
 * 並將其設置在 MDC（Mapped Diagnostic Context）中，以便在日誌中追蹤請求流。
 * <p>
 * 當請求中已經包含 {@code X-Trace-Id} 標頭時，會使用該 traceId；否則會生成一個新的唯一 ID。
 * 處理完請求後，會從 MDC 中移除 traceId，以免污染其他請求的日誌。
 */
public class TraceIdFilter extends GenericFilterBean {

    // 定義 traceId 的標頭名稱
    public static final String TRACE_ID_HEADER = "X-Trace-Id";

    /**
     * 執行過濾邏輯，為每個請求設置 traceId 並繼續處理過濾鏈。
     *
     * @param servletRequest  請求對象
     * @param servletResponse 回應對象
     * @param filterChain     過濾鏈
     * @throws IOException      輸出異常
     * @throws ServletException Servlet 異常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 將 ServletRequest 轉換為 HttpServletRequest，以便操作 HTTP 請求
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // 從請求標頭中提取 traceId，若無則生成新的唯一識別符
        String traceId = httpRequest.getHeader(TRACE_ID_HEADER);
        if (StrUtil.isBlank(traceId)) {
            traceId = IdUtil.objectId(); // 使用 Hutool 的 IdUtil 生成唯一識別符
        }

        // 將 traceId 設置到 MDC 中，方便日誌追蹤
        MDC.put("traceId", traceId);

        try {
            // 繼續執行過濾鏈
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 請求處理結束後，從 MDC 中移除 traceId，避免影響其他請求
            MDC.remove("traceId");
        }
    }
}
