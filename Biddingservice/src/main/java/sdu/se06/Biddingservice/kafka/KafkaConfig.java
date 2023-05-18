package sdu.se06.Biddingservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sdu.se06.auctioncommon.Model.BidRequest;


@Configuration
public class KafkaConfig {

    @Value("${kafka.topic}")
    private String topic;

    @Bean
    public NewTopic bidRequestTopic() {
        return TopicBuilder.name(topic).partitions(2).build();
    }

    @Bean
    public JsonSerializer<BidRequest> bidRequestJsonSerializer() {
        return new JsonSerializer<>();
    }
}