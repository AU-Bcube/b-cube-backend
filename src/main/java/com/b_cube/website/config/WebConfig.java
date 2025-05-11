import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // 모든 API 경로에 대해
                        .allowedOrigins("http://localhost:3000") // 프론트 주소
                        .allowedMethods("*") // 허용 메서드
                        .allowedHeaders("*")
                        .allowCredentials(true); // 쿠키 등 인증정보 허용
            }
        };
    }
}
