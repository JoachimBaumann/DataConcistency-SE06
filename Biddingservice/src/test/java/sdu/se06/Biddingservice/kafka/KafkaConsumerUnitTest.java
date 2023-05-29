package sdu.se06.Biddingservice.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;

import static org.mockito.Mockito.*;

public class KafkaConsumerUnitTest {

    private KafkaConsumer kafkaConsumer;

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        kafkaConsumer = new KafkaConsumer();
        kafkaConsumer.kafkaEventProducer = (kafkaEventProducer);
    }

    @Test
    public void testConsumeBiddingMessage() {
        // Create a sample BidRequest
        BidRequest bidRequest = new BidRequest();
        bidRequest.setSagaStatus(BidRequestState.PROCESSING);

        // Call the method under test
        kafkaConsumer.consumeBiddingMessage(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, times(1)).sendCatalogBid(bidRequest);
    }

    @Test
    public void testConsumeCatalogMessage_Approved() {
        // Create a sample BidRequest with approved state
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);

        // Call the method under test
        kafkaConsumer.consumeCatalogMessage(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, times(1)).sendAccountMessage(bidRequest);
        verify(kafkaEventProducer, never()).sendBidRejected(bidRequest);
    }

    @Test
    public void testConsumeCatalogMessage_Rejected() {
        // Create a sample BidRequest with rejected state
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);

        // Call the method under test
        kafkaConsumer.consumeCatalogMessage(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, never()).sendAccountMessage(bidRequest);
        verify(kafkaEventProducer, times(1)).sendBidRejected(bidRequest);
    }

    @Test
    public void testConsumeAccountProcessed_BothApproved() {
        // Create a sample BidRequest with both approved states
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);
        bidRequest.setAccountbidRequestState(BidRequestState.APPROVED);

        // Call the method under test
        kafkaConsumer.consumeAccountProcessed(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, times(1)).sendUpdateCatalog(bidRequest);
        verify(kafkaEventProducer, never()).sendBidRejected(bidRequest);
    }

    @Test
    public void testConsumeAccountProcessed_NotBothApproved() {
        // Create a sample BidRequest with different states
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);
        bidRequest.setAccountbidRequestState(BidRequestState.REJECTED);

        // Call the method under test
        kafkaConsumer.consumeAccountProcessed(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, never()).sendUpdateCatalog(bidRequest);
        verify(kafkaEventProducer, times(1)).sendBidRejected(bidRequest);
    }

    @Test
    public void testBidRequestFinished_RejectedState() {
        // Create a sample BidRequest with a rejected state
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.REJECTED);

        // Call the method under test
        kafkaConsumer.bidRequestFinished(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, times(1)).sendBidRejected(bidRequest);
        verify(kafkaEventProducer, never()).sendBidApproved(bidRequest);
    }

    @Test
    public void testBidRequestFinished_ApprovedState() {
        // Create a sample BidRequest with an approved state
        BidRequest bidRequest = new BidRequest();
        bidRequest.setCatalogBidRequestState(BidRequestState.APPROVED);

        // Call the method under test
        kafkaConsumer.bidRequestFinished(bidRequest);

        // Verify the behavior
        verify(kafkaEventProducer, never()).sendBidRejected(bidRequest);
        verify(kafkaEventProducer, times(1)).sendBidApproved(bidRequest);
    }
}
