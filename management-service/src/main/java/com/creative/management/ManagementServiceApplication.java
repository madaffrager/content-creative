package com.creative.management;

import com.creative.management.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementServiceApplication.class, args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (interceptors.isEmpty()){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }else{
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
    }
}
