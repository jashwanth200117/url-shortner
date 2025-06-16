// gateway-service/src/main/java/com/example/gateway/config/GatewayConfig.java

package com.example.gateway.config;

import com.example.gateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewayConfig {

    @Bean
    public WebFilter jwtWebFilter(JwtAuthenticationFilter filter) {
        return filter;
    }
}
