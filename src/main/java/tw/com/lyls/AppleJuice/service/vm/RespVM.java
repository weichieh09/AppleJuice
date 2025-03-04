package tw.com.lyls.AppleJuice.service.vm;

import cn.hutool.http.HttpStatus;
import lombok.Data;
import org.slf4j.MDC;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;

@Data
public class RespVM {

    private String traceId;
    private String code;
    private String message;
    private Object data;

    public RespVM(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
        this.traceId = MDC.get("traceId");
        this.data = "";
    }

    public RespVM(Object data) {
        this.code = HttpStatus.HTTP_OK + "";
        this.message = "";
        this.traceId = MDC.get("traceId");
        this.data = data;
    }

}
