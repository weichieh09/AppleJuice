package tw.com.lyls.AppleJuice.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
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
        if (StrUtil.isBlank(token)) {
            return null;
        }
        JWT jwt = JWTUtil.parseToken(token);
        return (String) jwt.getPayload("username");
    }

    @Override
    public boolean validateToken(String token) {
        try {
            // 驗證 token 簽名是否正確。
            JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256(SECRET_KEY.getBytes()));
            // 驗證 token 是否過期。
            JWTValidator.of(token).validateDate();
            return true;
        } catch (Exception e) {
            log.warn("驗證 JWT 失敗，token：{}。", token, e);
            return false;
        }
    }
}
