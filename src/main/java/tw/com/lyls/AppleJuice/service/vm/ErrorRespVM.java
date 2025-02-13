package tw.com.lyls.AppleJuice.service.vm;

import lombok.Data;
import org.slf4j.MDC;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;

@Data
public class ErrorRespVM {

    private String traceId;
    private String code;
    private String message;

    public ErrorRespVM(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
        this.traceId = MDC.get("traceId");
    }

}
