package org.example.flowmanager.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka.topics")
@Getter
@Setter
public class KafkaTopics {
    private String fileUpdate;
    private String fileUpload;
    private String fileFailed;
    private String fileUpdateDlt;


}
