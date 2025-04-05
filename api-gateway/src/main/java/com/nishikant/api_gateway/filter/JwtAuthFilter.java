package com.nishikant.api_gateway.filter;


import lombok.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthFilter implements GlobalFilter {

//    @Value("${jwt.secret}")
//    private String secret;

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){

        String authHeader = exchange
                .getRequest()
                .getHeaders()
                .getFirst("Autherization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid authentication header")
        }

        try{
            String token = authHeader.substring(7);  // Removed Bearer

        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token: " + e.getMessage());
        }

        return chain
                .filter(exchange)   ;
    }

}
