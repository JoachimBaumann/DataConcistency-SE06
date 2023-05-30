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

class KafkaConsumerTest {

    @Mock
    private CatalogRepository catalogRepository;

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsumeMessage_ShouldSendProcessedBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(100.0);

        Listing listing = new Listing();
        listing.setListingPrice(90.0);

        when(catalogRepository.findById(bidRequest.getListingID())).thenReturn(Optional.of(listing));

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedbidRequest(argThat(argument -> {
            // Verify the processed bid request
            return argument.getListingID() == 1 &&
                    argument.getAmount() == 100.0 &&
                    argument.getCatalogBidRequestState() == BidRequestState.APPROVED;
        }));

        // Verify that catalogRepository.findById is called
        verify(catalogRepository, times(1)).findById(bidRequest.getListingID());
        // Verify that catalogRepository.save is not called
        verify(catalogRepository, never()).save(any(Listing.class));
        // Verify that kafkaEventProducer.sendCatalogComplete is not called
        verify(kafkaEventProducer, never()).sendCatalogComplete(any(BidRequest.class));
    }


    @Test
    void testConsumeMessage_ShouldRejectBidRequestWhenListingDoesNotExist() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(100.0);

        when(catalogRepository.findById(bidRequest.getListingID())).thenReturn(Optional.empty());

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedbidRequest(argThat(argument -> {
            // Verify the rejected bid request
            return argument.getListingID() == 1 &&
                    argument.getAmount() == 100.0 &&
                    argument.getCatalogBidRequestState() == BidRequestState.REJECTED &&
                    argument.getSource().equals("Listing doesnt exist");
        }));

        // Verify that catalogRepository.findById is called
        verify(catalogRepository, times(1)).findById(bidRequest.getListingID());
        // Verify that catalogRepository.save is not called
        verify(catalogRepository, never()).save(any(Listing.class));
        // Verify that kafkaEventProducer.sendCatalogComplete is not called
        verify(kafkaEventProducer, never()).sendCatalogComplete(any(BidRequest.class));
    }


    @Test
    void testConsumeMessage_ShouldRejectBidRequestWhenBidAmountIsNotSufficient() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(100.0);

        Listing listing = new Listing();
        listing.setListingPrice(120.0);

        when(catalogRepository.findById(bidRequest.getListingID())).thenReturn(Optional.of(listing));

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedbidRequest(argThat(argument -> {
            // Verify the rejected bid request
            return argument.getListingID() == 1 &&
                    argument.getAmount() == 100.0 &&
                    argument.getCatalogBidRequestState() == BidRequestState.REJECTED &&
                    argument.getSource().equals("Bid amount not sufficient");
        }));
    }

    @Test
    void testConsumeUpdateCatalog_ShouldUpdateCatalogAndSendCatalogComplete() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(150.0);

        Listing listing = new Listing();
        listing.setListingID(1);
        listing.setListingPrice(100.0);

        when(catalogRepository.findById(bidRequest.getListingID())).thenReturn(Optional.of(listing));

        // Act
        kafkaConsumer.consumeUpdateCatalog(bidRequest);

        // Assert
        verify(catalogRepository, times(1)).findById(bidRequest.getListingID());
        verify(catalogRepository, times(1)).save(any(Listing.class));
        verify(kafkaEventProducer, times(1)).sendCatalogComplete(argThat(argument -> {
            // Verify the catalog complete message
            return argument.getListingID() == 1 &&
                    argument.getAmount() == 150.0 &&
                    argument.getAccountbidRequestState() == BidRequestState.APPROVED;
        }));
    }

    @Test
    void testUpdateCatalog_ShouldSetRejectedStateWhenListingDoesNotExist() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(1);
        bidRequest.setAmount(150.0);

        when(catalogRepository.findById(bidRequest.getListingID())).thenReturn(Optional.empty());

        // Act
        BidRequest result = kafkaConsumer.updateCatalog(bidRequest);

        // Assert
        verify(catalogRepository, times(1)).findById(bidRequest.getListingID());
        verifyNoMoreInteractions(catalogRepository);
        verifyNoInteractions(kafkaEventProducer);

        // Verify the rejected bid request
        assert result != null;
        assert result.getListingID() == 1;
        assert result.getAmount() == 150.0;
        assert result.getCatalogBidRequestState() == BidRequestState.REJECTED;
        assert result.getSource().equals("Catalog update error");
    }
}



