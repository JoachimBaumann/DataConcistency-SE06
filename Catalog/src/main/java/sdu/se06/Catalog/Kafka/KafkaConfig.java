package sdu.se06.Catalog.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.processed}")
    private String topic;

    @Value("${kafka.topic.new}")
    private String topic2;

    @Value("${kafka.topic.finished}")
    private String topic3;

    @Bean
    public NewTopic bidVerifiedTopic() {
        return TopicBuilder.name(topic2).partitions(2).build();
    }

    @Bean
    public NewTopic bidRequestTopic() {
        return TopicBuilder.name(topic).partitions(2).build();
    }

    @Bean
    public NewTopic catalogCompleteTopic() {
        return TopicBuilder.name(topic3).partitions(2).build();
    }
}

