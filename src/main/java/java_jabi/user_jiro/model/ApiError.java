package java_jabi.user_jiro.model;

import lombok.Data;

@Data
public class ApiError {
    final boolean result;
    final String description;
}

