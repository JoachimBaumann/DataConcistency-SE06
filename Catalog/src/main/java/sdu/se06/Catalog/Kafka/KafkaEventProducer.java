package sdu.se06.Catalog.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;

@Component
public class KafkaEventProducer {

    private final KafkaTemplate<Integer, BidRequest> kafkaTemplate;

    @Value("kafka.topic.new")
    private String topic;

    private String topic2 = "Catalog-processed-topic";


    public KafkaEventProducer(KafkaTemplate<Integer, BidRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProcessedbidRequest(BidRequest bidRequest) {
        kafkaTemplate.send(topic2,bidRequest.getListingID(), bidRequest);
    }
}
