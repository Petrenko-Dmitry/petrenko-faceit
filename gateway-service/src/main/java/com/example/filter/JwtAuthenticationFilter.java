package com.example.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${baseUrl.user-service}")
    private String userServiceUrl;

    private final List<String> openApi = List.of(
            "/users/register",
            "/users/login"
    );

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            final var authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
            if (this.isSecured(exchange.getRequest())) {

                if (nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
                    try {
                        var jwt = authHeader.substring(7);
                        this.restTemplate.getForObject(this.userServiceUrl + "/users/validateToken?token=" + jwt, Object.class);
                    } catch (Exception e) {
                        exchange.getResponse().setStatusCode(UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                } else {
                    exchange.getResponse().setStatusCode(UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {}

    private boolean isSecured(ServerHttpRequest request) {
        return this.openApi.stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}

