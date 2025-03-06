package tw.com.lyls.AppleJuice.component;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;
import tw.com.lyls.AppleJuice.service.vm.RespVM;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespVM> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request) {
        log.warn("錯誤：{}，錯誤訊息：{}。", request.getRequestURI(), accessDeniedException.getMessage(), accessDeniedException);
        return ResponseEntity.status(HttpStatus.HTTP_FORBIDDEN)
                .body(new RespVM(ErrorEnum.HTTP_FORBIDDEN));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespVM> handleException(Exception exception, HttpServletRequest request) {
        log.error("錯誤：{}，未預期的錯誤訊息：{}。", request.getRequestURI(), exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR)
                .body(new RespVM(ErrorEnum.HTTP_INTERNAL_ERROR));
    }

    private void printLog(Exception exception, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("錯誤：{}，錯誤訊息：{}。", uri, exception.getMessage(), exception);
    }

}
