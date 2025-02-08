package tw.com.lyls.AppleJuice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // @formatter:off
        return new OpenAPI()
                .info(new Info()
                        .title("AppleJuice API 文件")
                        .version("25.02.08")
                        .description("龍巖資訊轉型部 AppleJuice 專案的 API 文件"));
        // @formatter:on
    }
}
