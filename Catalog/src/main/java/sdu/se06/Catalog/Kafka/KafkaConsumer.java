package sdu.se06.Catalog.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;


@Component
public class KafkaConsumer {



    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(BidRequest bidRequest) {
        System.out.println("Received message: " + bidRequest);
        // Process event
        System.out.println("we got a bid: " + bidRequest.time);

    }
}
