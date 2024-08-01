package gift.global.config;

import gift.global.auth.interceptor.AuthenticationInterceptor;
import gift.global.auth.interceptor.AuthorizationInterceptor;
import gift.global.auth.resolver.LoginInfoArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(
        AuthorizationInterceptor authorizationInterceptor,
        AuthenticationInterceptor authenticationInterceptor
    ) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //인증 인터셉터
        registry.addInterceptor(authenticationInterceptor).order(1);
        //인가 인터셉터
        registry.addInterceptor(authorizationInterceptor).order(2);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginInfoArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}

