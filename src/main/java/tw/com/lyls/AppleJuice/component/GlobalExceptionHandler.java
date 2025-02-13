package tw.com.lyls.AppleJuice.component;

import cn.hutool.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;
import tw.com.lyls.AppleJuice.service.vm.ErrorRespVM;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRespVM> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.HTTP_FORBIDDEN)
                .body(new ErrorRespVM(ErrorEnum.HTTP_FORBIDDEN));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespVM> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.HTTP_INTERNAL_ERROR)
                .body(new ErrorRespVM(ErrorEnum.HTTP_INTERNAL_ERROR));
    }

}
