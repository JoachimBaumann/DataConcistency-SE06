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

    // Catalog processed
    @KafkaListener(topics = "Catalog-processed-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCatalogMessage(BidRequest bidRequest) {
        // Process event

        if (bidRequest.getCatalogBidRequestState().equals(BidRequestState.APPROVED)) {
            kafkaEventProducer.sendAccountMessage(bidRequest);
        } else {
            // TODO Process event implement rollback.
            bidRequest.setCatalogBidRequestState(BidRequestState.ROLLBACK);
        }
    }


    // New bids
    @KafkaListener(topics = "${kafka.topic.new.bid}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeBiddingMessage(BidRequest bidRequest) {
        System.out.println("Received message: " + bidRequest);

        // TODO Process event
        kafkaEventProducer.sendCatalogBid(bidRequest);

    }

    @KafkaListener(topics = "${kafka.topic.account.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAccountProcessed(BidRequest bidRequest) {
        System.out.println("Received from account processed: " + bidRequest);
        // TODO Process event


        //kafkaEventProducer.sendCatalogBid(bidRequest);

    }
}
