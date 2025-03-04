package tw.com.lyls.AppleJuice.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc 設定類別，負責 OpenAPI 規範的相關設定。
 */
@Configuration
public class SpringDocConfig {

    /**
     * 設定 OpenAPI 文件的基本資訊。
     *
     * @return 自訂的 OpenAPI 物件
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // @formatter:off
        return new OpenAPI()
                .info(new Info()
                        .title("AppleJuice API 文件") // 設定 API 文件的標題
                        .version("25.02.08") // 設定 API 版本
                        .description("龍巖資訊轉型部 AppleJuice 專案的 API 文件")); // 設定 API 文件的描述
        // @formatter:on
    }
}
