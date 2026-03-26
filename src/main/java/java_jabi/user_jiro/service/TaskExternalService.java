package java_jabi.user_jiro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TaskExternalService {
    private final RestClient restClient;
    @Value("${app.url}")
    private String url;

    public TaskExternalService() {
        if (url == null) {
            this.restClient = RestClient.builder().baseUrl("http://localhost:8086/api/v2").build();
        } else {
            this.restClient = RestClient.builder().baseUrl(url).build();
        }
    }

    public boolean checkExistTasksByUser(long userId) {
        return Boolean.TRUE.equals(restClient.get().uri("/tasks/existbyuser/{id}", userId).retrieve().body(Boolean.class));
    }
}
