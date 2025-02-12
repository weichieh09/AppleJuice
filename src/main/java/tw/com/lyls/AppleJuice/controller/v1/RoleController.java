package tw.com.lyls.AppleJuice.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.lyls.AppleJuice.service.JwtService;
import tw.com.lyls.AppleJuice.service.vm.LoginReqVM;

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
    public ResponseEntity<String> login(@RequestBody LoginReqVM reqVM) {
        try {
            // 使用 AuthenticationManager 驗證使用者
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqVM.getUsername(), reqVM.getPassword())
            );
            // 驗證成功，產生 JWT
            String token = jwtService.generateToken(reqVM.getUsername());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            log.error("登入失敗，帳號：{}。", reqVM.getUsername(), e);
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
