package com.creative.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TrackingFilter implements GlobalFilter {

    @Autowired
    FilterUtils filterUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
   
    @Override
    // Code that executes everytime when a request passes through filter
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeader = exchange.getRequest().getHeaders(); // HTTP header from request

        if (isCorrelationIdPresent(requestHeader)){
            LOGGER.info("API Gateway | TrackingFilter | filter | correlation_id found in trackingfilter: {}",filterUtils.getCorrelationId(requestHeader));
        }else{
            String correlationId =  generateCorrelationId(); // if correlation_id not found in header, generate one
            exchange = filterUtils.setCorrelationId(exchange,correlationId);
            LOGGER.info("API Gateway | TrackingFilter | filter | correlation_id generated in tracking filter: {}",correlationId);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeader) {
        return filterUtils.getCorrelationId(requestHeader)!=null;
    }
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
