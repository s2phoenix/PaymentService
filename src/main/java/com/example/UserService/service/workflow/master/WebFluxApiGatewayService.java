package com.example.UserService.service.workflow.master;

import com.example.UserService.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebFluxApiGatewayService {

    private final WebClient webClient;

    @Value("${api.base-url}")
    private String baseUrl;

    public WebFluxApiGatewayService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    // Generic API call to external service (Reactive, non-blocking)
    public <T> Mono<T> callApi(String endpoint, Class<T> responseType, Object... params) {
        return webClient.get()
                .uri(endpoint, params)  // Set the endpoint and parameters
                .retrieve()
                .bodyToMono(responseType);  // Convert the response body to Mono<T>
    }

    // Example method to call User Service API
    public Mono<UserInfo> getUserInfo(Long userId) {
        String endpoint = "/api/user/{id}";
        return callApi(endpoint, UserInfo.class, userId);
    }
}
