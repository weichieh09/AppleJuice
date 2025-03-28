package tw.com.lyls.AppleJuice.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.lyls.AppleJuice.domain.mysql.User;
import tw.com.lyls.AppleJuice.util.SecurityUtils;

@Slf4j
@RestController
@RequestMapping("/api/v2/demo")
@Tag(name = "Demo API", description = "示範 SpringDoc OpenAPI 使用方式")
public class DemoController {

    @GetMapping
    @Operation(summary = "示範 GET 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoGet() {
        log.info("DemoController.demoGet() called");
        User currentUser = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

    @PostMapping
    @Operation(summary = "示範 POST 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoPost() {
        log.info("DemoController.demoPost() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

    @DeleteMapping
    @Operation(summary = "示範 DELETE 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoDelete() {
        log.info("DemoController.demoDelete() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

    @PutMapping
    @Operation(summary = "示範 PUT 方法", description = "示範 SpringDoc OpenAPI 使用方式")
    public ResponseEntity<String> demoPut() {
        log.info("DemoController.demoPut() called");
        return ResponseEntity.ok().body("嗨！資轉部，來杯 Apple Juice 嗎？");
    }

}
