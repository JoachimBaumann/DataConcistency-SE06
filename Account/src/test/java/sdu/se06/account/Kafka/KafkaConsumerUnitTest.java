package sdu.se06.account.Kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sdu.se06.account.Controller.AccountRepository;
import sdu.se06.account.entity.AccountEntity;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KafkaConsumerUnitTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeMessage_ShouldSendProcessedRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);

        // Mock the KafkaEventProducer to verify the method call
        KafkaEventProducer kafkaEventProducer = mock(KafkaEventProducer.class);
        kafkaConsumer.kafkaEventProducer = (kafkaEventProducer);

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedRequest(any(BidRequest.class));
    }

    @Test
    public void testConsumeRejectedBids_ShouldRollBack() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setAccountbidRequestState(BidRequestState.APPROVED);
        bidRequest.setSagaStatus(BidRequestState.ROLLBACK);
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);

        // Mock the rollBack method to verify the account balance update
        KafkaConsumer kafkaConsumerSpy = spy(kafkaConsumer);
        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        kafkaConsumerSpy.consumeRejectedBids(bidRequest);

        // Assert
        assertEquals(600.0, account.getBalance());
        verify(repository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    public void testNewBidRequest_WithSufficientFunds_ShouldApproveBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);

        // Mock the repository to return an account with sufficient balance
        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        BidRequest result = kafkaConsumer.newBidRequest(bidRequest);

        // Assert
        assertEquals(BidRequestState.APPROVED, result.getAccountbidRequestState());
        assertEquals(400.0, account.getBalance());
    }


    @Test
    public void testNewBidRequest_WithInsufficientFunds_ShouldRejectBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(1000.0);

        // Mock the repository to return an account with insufficient balance
        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        BidRequest result = kafkaConsumer.newBidRequest(bidRequest);

        // Assert
        assertEquals(BidRequestState.REJECTED, result.getAccountbidRequestState());
        assertEquals("Insufficient funds", result.getSource());
        assertEquals(500.0, account.getBalance());
        verify(repository, times(0)).save(any(AccountEntity.class));
    }
}
