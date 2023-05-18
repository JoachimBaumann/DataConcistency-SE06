package sdu.se06.Biddingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;


@Component
public class KafkaConsumer {

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(BidRequest bidRequest) {
        System.out.println("Received message: " + bidRequest);
        // Process event
        kafkaEventProducer.sendCatalogBid(bidRequest);

    }

    @KafkaListener(topics = "${kafka.topic.account.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAccountProcessed(BidRequest bidRequest) {
        System.out.println("Received from account processed: " + bidRequest);
        // Process event
        kafkaEventProducer.sendCatalogBid(bidRequest);

    }
}
