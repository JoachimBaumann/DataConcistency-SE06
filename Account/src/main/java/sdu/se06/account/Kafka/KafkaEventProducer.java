package sdu.se06.account.Kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;

@Component
public class KafkaEventProducer {

    private final KafkaTemplate<Integer, BidRequest> kafkaTemplate;

    @Value("kafka.topic.new")
    private String topic;
    @Value("Kafka.topic.processed")
    private String topic2;



    public KafkaEventProducer(KafkaTemplate<Integer, BidRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProcessedRequest(BidRequest bidRequest){
        kafkaTemplate.send(topic2,bidRequest.getListingID(), bidRequest);
    }
}
