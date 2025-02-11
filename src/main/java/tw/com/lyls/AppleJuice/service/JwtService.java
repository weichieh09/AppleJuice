package tw.com.lyls.AppleJuice.service;

public interface JwtService {

    // 生成 Token
    String generateToken(String username);

    // 提取 Token 中的使用者名稱
    String extractUsername(String token);

    // 驗證 Token
    boolean validateToken(String token);

}
