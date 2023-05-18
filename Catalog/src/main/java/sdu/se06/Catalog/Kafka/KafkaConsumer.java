package sdu.se06.Catalog.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sdu.se06.Catalog.model.Listing;
import sdu.se06.Catalog.ListingController.CatalogRepository;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;

import java.util.Optional;


@Component
public class KafkaConsumer {

    @Autowired
    CatalogRepository repository;
    @Autowired
    KafkaEventProducer kafkaEventProducer;

    private final double bidmultiplier = 1.1;


    @KafkaListener(topics = "${kafka.topic.new}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(BidRequest bidRequest) {
        BidRequest newBidRequest = verifyNewBid(bidRequest);
        kafkaEventProducer.sendProcessedbidRequest(newBidRequest);
    }


    private BidRequest verifyNewBid(BidRequest bidRequest) {

        try {


            // VERIFY LISTING INFORMATION

            Optional<Listing> listingData = repository.findById(bidRequest.getListingID());

            if (listingData.isPresent()) {
                // Check price
                if (listingData.get().getListingPrice() * bidmultiplier <= bidRequest.getAmount()) {
                    bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);
                    System.out.println("this should be sent: " + bidRequest);
                    return bidRequest;
                }
            } else {
                //reject if price is below allowed
                bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);
                System.out.println("Data not present for bid: " + bidRequest);
                return bidRequest;
            }
            bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);
            return bidRequest;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);
            return bidRequest;
        }

    }
}
