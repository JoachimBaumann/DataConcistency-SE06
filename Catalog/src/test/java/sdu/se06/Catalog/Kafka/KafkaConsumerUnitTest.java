package sdu.se06.Catalog.Kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sdu.se06.Catalog.ListingController.CatalogRepository;
import sdu.se06.Catalog.model.Listing;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class KafkaConsumerUnitTest {

    @Mock
    private CatalogRepository repository;

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeMessage_ShouldSendProcessedBidRequest() {
        // Create a sample bid request
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(100.0);

        // Create a mock Listing object
        Listing listing = new Listing();
        listing.setListingPrice(90.0);

        // Mock the repository behavior
        when(repository.findById(1)).thenReturn(Optional.of(listing));

        // Call the method under test
        kafkaConsumer.consumeMessage(bidRequest);

        // Verify that the repository method was called
        verify(repository).findById(1);

        // Verify that the processed bid request was sent
        verify(kafkaEventProducer).sendProcessedbidRequest(eq(bidRequest));
    }

    @Test
    public void testConsumeMessage_WithInvalidListing_ShouldSendRejectedBidRequest() {
        // Create a sample bid request
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);

        // Mock the repository behavior
        when(repository.findById(1)).thenReturn(Optional.empty());

        // Call the method under test
        kafkaConsumer.consumeMessage(bidRequest);

        // Verify that the repository method was called
        verify(repository).findById(1);

        // Verify that the rejected bid request was sent
        verify(kafkaEventProducer).sendProcessedbidRequest(argThat(processedBidRequest ->
                processedBidRequest.getCatalogBidRequestState() == BidRequestState.REJECTED
                        && processedBidRequest.getSource().equals("Listing doesnt exist")));
    }

    @Test
    public void testConsumeUpdateCatalog_ShouldSendCatalogComplete() {
        // Create a sample bid request
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(100.0);

        // Create a mock Listing object
        Listing listing = new Listing();

        // Mock the repository behavior
        when(repository.findById(1)).thenReturn(Optional.of(listing));

        // Call the method under test
        kafkaConsumer.consumeUpdateCatalog(bidRequest);

        // Verify that the repository method was called
        verify(repository).findById(1);

        // Verify that the catalog complete event was sent
        verify(kafkaEventProducer).sendCatalogComplete(bidRequest);

    }
}
