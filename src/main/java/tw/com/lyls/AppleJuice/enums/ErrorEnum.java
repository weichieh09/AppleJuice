package tw.com.lyls.AppleJuice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    // @formatter:off

    HTTP_UNAUTHORIZED("401", "Unauthorized"),
    HTTP_FORBIDDEN("403", "Forbidden"),
    HTTP_INTERNAL_ERROR("500", "Internal Server Error")
    ;

    // @formatter:on

    private final String code;
    private final String message;

}
