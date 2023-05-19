package sdu.se06.Catalog.Kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;

@Component
public class KafkaEventProducer {

    private final KafkaTemplate<Integer, BidRequest> kafkaTemplate;


    @Value("${Kafka.topic.processed}")
    private String topic2;

    @Value("${kafka.topic.finished}")
    private String bidSagaDone;


    public KafkaEventProducer(KafkaTemplate<Integer, BidRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProcessedbidRequest(BidRequest bidRequest) {
        kafkaTemplate.send(topic2,bidRequest.getListingID(), bidRequest);
    }

    public void sendBidSagaDone(BidRequest bidRequest) {
        kafkaTemplate.send(bidSagaDone,bidRequest.getListingID(),bidRequest);
    }
}
