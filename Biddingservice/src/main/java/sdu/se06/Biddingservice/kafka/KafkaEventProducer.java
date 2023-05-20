package sdu.se06.Biddingservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sdu.se06.auctioncommon.Model.BidRequest;


@Component
public class KafkaEventProducer {

    private final KafkaTemplate<Integer, BidRequest> kafkaTemplate;

    @Value("${kafka.topic.new.bid}")
    private String topic;
    private final String accountTopic = "Account-topic";

    private final String updateCatalogTopic = "Catalog-update-topic";

    private final String bidRejectedTopic = "bid-rejected-topic";

    private final String bidApproved = "bid-approved-topic";

    @Autowired
    public KafkaEventProducer(KafkaTemplate<Integer, BidRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBidRequest(BidRequest bidRequest) {
        kafkaTemplate.send(topic,bidRequest.getListingID(), bidRequest);
    }

    public void sendBidRejected(BidRequest bidRequest) {
        kafkaTemplate.send(bidRejectedTopic,bidRequest.getListingID(),bidRequest);
    }

    public void sendUpdateCatalog(BidRequest bidRequest) {
        kafkaTemplate.send(updateCatalogTopic,bidRequest.getListingID(),bidRequest);
    }

    public void sendAccountMessage(BidRequest bidRequest) {
        kafkaTemplate.send(accountTopic, bidRequest.getListingID(),bidRequest);
    }

    public void sendCatalogBid(BidRequest bidRequest) {
        String topic2 = "Catalog-topic";
        kafkaTemplate.send(topic2,bidRequest.getListingID(), bidRequest);
    }
    public void sendBidApproved(BidRequest bidRequest) {
        kafkaTemplate.send(bidApproved,bidRequest.getListingID(), bidRequest);
    }
}
