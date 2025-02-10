package tw.com.lyls.AppleJuice.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.com.lyls.AppleJuice.service.JwtService;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration}")
    private Integer EXPIRATION;

    @Override
    public String generateToken(String username) {
        return JWT.create()
                .setKey(SECRET_KEY.getBytes()) // 設定密鑰
                .setIssuedAt(DateUtil.date()) // 簽發時間
                .setExpiresAt(DateUtil.offsetSecond(DateUtil.date(), EXPIRATION)) // 過期時間(秒)
                .setPayload("username", username) // 使用者名稱
                .sign();
    }

    @Override
    public String extractUsername(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        JWT jwt = JWTUtil.parseToken(token);
        return (String) jwt.getPayload("username");
    }

    @Override
    public boolean validateToken(String token) {
        // 驗證 token 是否有效。
        boolean verify = JWTUtil.verify(token, SECRET_KEY.getBytes());
        if (!verify) {
            log.error("Token：{} is invalid.", token);
            return false;
        }
        // 驗證 token 是否過期。
        try {
            JWTValidator.of(token).validateDate();
            return true;
        } catch (Exception e) {
            log.error("Token：{} is expired: {}", token, e.getMessage());
            return false;
        }
    }
}
