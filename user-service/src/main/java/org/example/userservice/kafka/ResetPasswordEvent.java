package org.example.userservice.kafka;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordEvent {
    String to;
    String subject;
    String content;
    Map<String, Object> variables;
}
