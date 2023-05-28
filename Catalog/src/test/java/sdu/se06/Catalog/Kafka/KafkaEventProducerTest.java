package sdu.se06.Catalog.Kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import sdu.se06.auctioncommon.Model.BidRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class KafkaEventProducerTest {

    @Mock
    private KafkaTemplate<Integer, BidRequest> kafkaTemplateMock;

    @InjectMocks
    private KafkaEventProducer kafkaEventProducer;

    @Captor
    private ArgumentCaptor<String> topicCaptor;

    @Captor
    private ArgumentCaptor<Integer> keyCaptor;

    @Captor
    private ArgumentCaptor<BidRequest> messageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaEventProducer.topic2 = "Catalog-processed-topic"; // Inject the topic value manually
        kafkaEventProducer.bidSagaDone = "Catalog-finished-topic"; // Inject the topic value manually
    }

    @Test
    public void testSendProcessedbidRequest() {
        // Create a sample BidRequest for testing
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(123);

        // Call the method under test
        kafkaEventProducer.sendProcessedbidRequest(bidRequest);

        // Verify that kafkaTemplate.send() was called with the expected arguments
        verify(kafkaTemplateMock).send(topicCaptor.capture(), keyCaptor.capture(), messageCaptor.capture());

        // Assert the captured values
        assertEquals("Catalog-processed-topic", topicCaptor.getValue());
        assertEquals(123, keyCaptor.getValue());
        assertEquals(bidRequest, messageCaptor.getValue());
    }


    @Test
    public void testSendCatalogComplete() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setListingID(123);

        // Act
        kafkaEventProducer.sendCatalogComplete(bidRequest);

        // Assert
        verify(kafkaTemplateMock).send("Catalog-finished-topic", bidRequest.getListingID(), bidRequest);
    }
}

