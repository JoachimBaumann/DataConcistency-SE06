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


    // New bids
    @KafkaListener(topics = "${kafka.topic.new.bid}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeBiddingMessage(BidRequest bidRequest) {
        System.out.println("Event from NewBid topic: " + bidRequest);
        bidRequest.setSagaStatus(BidRequestState.PROCESSING);
        kafkaEventProducer.sendCatalogBid(bidRequest);
    }

    // Catalog processed
    @KafkaListener(topics = "${kafka.topic.Catalog.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCatalogMessage(BidRequest bidRequest) {
        // Process event
        System.out.println("Event from Catalog-processed-topic" + bidRequest);
        if (bidRequest.getCatalogBidRequestState().equals(BidRequestState.APPROVED)) {
            kafkaEventProducer.sendAccountMessage(bidRequest);
        } else {
            bidRequest.setSagaStatus(BidRequestState.REJECTED);
            kafkaEventProducer.sendBidRejected(bidRequest);
        }
    }


    @KafkaListener(topics = "${kafka.topic.account.processed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAccountProcessed(BidRequest bidRequest) {
        System.out.println("Event from account processed topic: " + bidRequest);
        // IF Both approved
        if (bidRequest.getCatalogBidRequestState().equals(BidRequestState.APPROVED) && bidRequest.getAccountbidRequestState().equals(BidRequestState.APPROVED)) {
            // Update catalog
            kafkaEventProducer.sendUpdateCatalog(bidRequest);
        } else {
            // Time for roll back
            bidRequest.setSagaStatus(BidRequestState.REJECTED);
            kafkaEventProducer.sendBidRejected(bidRequest);
        }
    }


    @KafkaListener(topics = "${kafka.topic.Catalog.finished}", groupId = "${spring.kafka.consumer.group-id}")
    public void bidRequestFinished(BidRequest bidRequest) {

        if (bidRequest.getCatalogBidRequestState().equals(BidRequestState.REJECTED)) {
            bidRequest.setSagaStatus(BidRequestState.ROLLBACK);
            System.out.println("saga rejected " + bidRequest);
            kafkaEventProducer.sendBidRejected(bidRequest);
        } else {
            bidRequest.setSagaStatus(BidRequestState.APPROVED);
            System.out.println("saga complete: " + bidRequest);
            kafkaEventProducer.sendBidApproved(bidRequest);
        }
    }
}
