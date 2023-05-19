package sdu.se06.Catalog.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sdu.se06.Catalog.ListingController.CatalogRepository;
import sdu.se06.Catalog.model.Listing;
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

    @KafkaListener(topics = "${kafka.topic.update}")
    public void consumeUpdateCatalog(BidRequest bidRequest) {
        Optional<Listing> listingData = repository.findById(bidRequest.getListingID());

        //TODO add rest of method
        if(listingData.isPresent()) {
            Listing listing = listingData.get();

            listing.setListingPrice(bidRequest.getAmount());
            repository.save(listing);
            System.out.println("Updated price in catalog from bid: " + bidRequest);
            bidRequest.setSource("Catalog Updated");
            kafkaEventProducer.sendBidSagaDone(bidRequest);
        } else {
            bidRequest.setAccountbidRequestState(BidRequestState.ROLLBACK);
            kafkaEventProducer.sendBidSagaDone(bidRequest);
        }
    }


    private BidRequest verifyNewBid(BidRequest bidRequest) {


        // VERIFY LISTING INFORMATION

        Optional<Listing> listingData = repository.findById(bidRequest.getListingID());

        if (listingData.isPresent()) {
            // Check price
            if (listingData.get().getListingPrice() * bidmultiplier <= bidRequest.getAmount()) {
                bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);
                return bidRequest;
            }
        } else {
            //reject if price is below allowed
            bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);
            return bidRequest;
        }
        bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);
        return bidRequest;
    }

}
