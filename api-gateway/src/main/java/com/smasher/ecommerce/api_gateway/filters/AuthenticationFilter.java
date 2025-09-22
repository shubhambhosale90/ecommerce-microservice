package com.smasher.ecommerce.api_gateway.filters;

import com.smasher.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!config.isEnabled) return chain.filter(exchange);

            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authorizationHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);

            ServerHttpRequest mutatedRequest = (ServerHttpRequest) exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", userId.toString())
                    .build();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);
        };
    }

    @Data
    public static class Config {
        private boolean isEnabled;
    }
}
