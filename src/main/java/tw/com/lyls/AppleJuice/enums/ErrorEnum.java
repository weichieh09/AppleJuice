package tw.com.lyls.AppleJuice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    // @formatter:off

    HTTP_UNAUTHORIZED("401", "身份驗證錯誤：Unauthorized"),
    HTTP_FORBIDDEN("403", "權限驗證錯誤：Forbidden"),
    HTTP_INTERNAL_ERROR("500", "伺服器錯誤：Internal Server Error"),
    ;

    // @formatter:on

    private final String code;
    private final String message;

}
