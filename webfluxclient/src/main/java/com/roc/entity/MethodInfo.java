package com.roc.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@SuperBuilder
public class MethodInfo {
    private String url;
    private HttpMethod method;
    private Map<String,Object> params;
    private Mono<?> body;
}
