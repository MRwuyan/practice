package com.roc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {
    private String url;
    private HttpMethod method;
    private Map<String,Object> params;
    private Mono<?> body;
}
