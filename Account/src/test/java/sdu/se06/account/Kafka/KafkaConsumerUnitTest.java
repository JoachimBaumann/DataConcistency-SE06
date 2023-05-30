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
    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeMessage_WithSufficientFunds_ShouldApproveBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);

        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedRequest(bidRequest);
        assertEquals(BidRequestState.APPROVED, bidRequest.getAccountbidRequestState());
        assertEquals(400.0, account.getBalance());
    }

    @Test
    public void testConsumeMessage_WithInsufficientFunds_ShouldRejectBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(1000.0);

        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedRequest(bidRequest);
        assertEquals(BidRequestState.REJECTED, bidRequest.getAccountbidRequestState());
        assertEquals("Insufficient funds", bidRequest.getSource());
        assertEquals(500.0, account.getBalance());
    }

    @Test
    public void testConsumeMessage_WithNonexistentAccount_ShouldRejectBidRequest() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);

        when(repository.findById(123)).thenReturn(Optional.empty());

        // Act
        kafkaConsumer.consumeMessage(bidRequest);

        // Assert
        verify(kafkaEventProducer, times(1)).sendProcessedRequest(bidRequest);
        assertEquals(BidRequestState.REJECTED, bidRequest.getAccountbidRequestState());
        assertEquals("Account not found!", bidRequest.getSource());
    }

    @Test
    public void testConsumeRejectedBids_WithApprovedBidRequest_ShouldRollbackChanges() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);
        bidRequest.setAccountbidRequestState(BidRequestState.APPROVED);
        bidRequest.setSagaStatus(BidRequestState.ROLLBACK);

        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        kafkaConsumer.consumeRejectedBids(bidRequest);

        // Assert
        verify(repository, times(1)).save(account);
        assertEquals(600.0, account.getBalance());
    }

    @Test
    public void testConsumeRejectedBids_WithRejectedBidRequest_ShouldNotRollbackChanges() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(1000.0);
        bidRequest.setAccountbidRequestState(BidRequestState.REJECTED);
        bidRequest.setSagaStatus(BidRequestState.ROLLBACK);

        AccountEntity account = new AccountEntity();
        account.setId(123);
        account.setBalance(500.0);
        when(repository.findById(123)).thenReturn(Optional.of(account));

        // Act
        kafkaConsumer.consumeRejectedBids(bidRequest);

        // Assert
        verify(repository, never()).save(account);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    public void testConsumeRejectedBids_WithNonexistentAccount_ShouldNotRollbackChanges() {
        // Arrange
        BidRequest bidRequest = new BidRequest();
        bidRequest.setUserID(123);
        bidRequest.setAmount(100.0);
        bidRequest.setAccountbidRequestState(BidRequestState.APPROVED);
        bidRequest.setSagaStatus(BidRequestState.ROLLBACK);

        when(repository.findById(123)).thenReturn(Optional.empty());

        // Act
        kafkaConsumer.consumeRejectedBids(bidRequest);

        // Assert
        verify(repository, never()).save(any());
    }
}
