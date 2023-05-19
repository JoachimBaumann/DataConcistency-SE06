package sdu.se06.Biddingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;


@Service
public class KafkaConsumer {

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    // Catalog processed
    @KafkaListener(topics = "${kafka.topic.Catalog.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCatalogMessage(BidRequest bidRequest) {
        // Process event
        System.out.println("Event from Catalog-processed-topic" + bidRequest);
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
        System.out.println("Event from NewBid topic: " + bidRequest);

        // TODO Process event
        kafkaEventProducer.sendCatalogBid(bidRequest);

    }

    @KafkaListener(topics = "${kafka.topic.Catalog.finished}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSagaDoneMessage(BidRequest bidRequest) {

        System.out.println("saga done: " + bidRequest);

        // TODO Process event


    }

    @KafkaListener(topics = "${kafka.topic.account.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAccountProcessed(BidRequest bidRequest) {
        System.out.println("Event from account processed topic: " + bidRequest);
        // TODO Process event

        // IF Both approved
        if (bidRequest.getCatalogBidRequestState().equals(BidRequestState.APPROVED) && bidRequest.getAccountbidRequestState().equals(BidRequestState.APPROVED)) {
            // Update catalog
            kafkaEventProducer.sendUpdateCatalog(bidRequest);
        } else {
            // Time for roll back
        }


        //kafkaEventProducer.sendCatalogBid(bidRequest);

    }
}
