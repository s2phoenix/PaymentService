package com.example.UserService.service.workflow.master;

import com.example.UserService.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateApiGatewayService {

    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    public RestTemplateApiGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Generic API call to external service
    public <T> T callApi(String endpoint, Class<T> responseType, Object... params) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + endpoint).buildAndExpand(params).toUriString();
        return restTemplate.getForObject(url, responseType);
    }

    // Example method to call User Service API
    public UserInfo getUserInfo(Long userId) {
        String endpoint = "/api/user/{id}";
        return callApi(endpoint, UserInfo.class, userId);
    }
}
