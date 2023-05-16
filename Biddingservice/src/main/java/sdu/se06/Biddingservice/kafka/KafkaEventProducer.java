package sdu.se06.Biddingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;


@Component
public class KafkaEventProducer {

    private final KafkaTemplate<String, BidRequest> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;
    private String topic2 = "Catalog-topic";

    @Autowired
    public KafkaEventProducer(KafkaTemplate<String, BidRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBidRequest(BidRequest bidRequest) {
        kafkaTemplate.send(topic,Integer.toString(bidRequest.getListingID()), bidRequest);
    }

    public void sendCatalogBid(BidRequest bidRequest) {
        kafkaTemplate.send(topic2,Integer.toString(bidRequest.getListingID()), bidRequest);
    }
}