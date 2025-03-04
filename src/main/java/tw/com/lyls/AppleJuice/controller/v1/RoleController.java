package tw.com.lyls.AppleJuice.controller.v1;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tw.com.lyls.AppleJuice.enums.ErrorEnum;
import tw.com.lyls.AppleJuice.service.JwtService;
import tw.com.lyls.AppleJuice.service.vm.LoginReqVM;
import tw.com.lyls.AppleJuice.service.vm.RespVM;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "Demo API", description = "示範 SpringDoc OpenAPI 使用方式")
public class RoleController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "登入", description = "使用者登入")
    public ResponseEntity<RespVM> login(@RequestBody LoginReqVM reqVM) {
        try {
            // 使用 AuthenticationManager 驗證使用者
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqVM.getUsername(), reqVM.getPassword())
            );

            // 驗證成功，產生 JWT
            String token = jwtService.generateToken(reqVM.getUsername());

            // 封裝
            Map<String, String> resultMap = MapUtil.builder("token", token).build();

            return ResponseEntity.ok(new RespVM(resultMap));
        } catch (Exception e) {
            log.error("登入失敗，帳號：{}。", reqVM.getUsername(), e);
            return ResponseEntity.status(HttpStatus.HTTP_UNAUTHORIZED).body(new RespVM(ErrorEnum.HTTP_UNAUTHORIZED));
        }
    }

    @GetMapping("/test")
    @Operation(summary = "測試", description = "測試 API")
    public ResponseEntity<RespVM> test() {
        throw new RuntimeException("測試例外");
    }
}
