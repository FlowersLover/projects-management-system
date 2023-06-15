package com.digitaldesign.murashkina.services.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {
    private String to;
    private String subject;
    private String templateLocation;
    private Map<String, Object> context;
}
