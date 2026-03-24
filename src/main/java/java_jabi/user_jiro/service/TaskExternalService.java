package java_jabi.user_jiro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

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

    public boolean checkManagerRole(long userId) {
        if (Objects.equals(restClient.get().uri("/tasks/existbyuser", userId).retrieve().body(String.class), "MANAGER")) {
            return true;
        }
        return false;
    }
}
