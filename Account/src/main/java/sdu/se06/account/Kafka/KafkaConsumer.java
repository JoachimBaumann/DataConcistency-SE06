package sdu.se06.account.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sdu.se06.account.Controller.AccountRepository;
import sdu.se06.account.entity.AccountEntity;
import sdu.se06.auctioncommon.Model.BidRequest;
import sdu.se06.auctioncommon.Model.BidRequestState;

import java.util.Optional;

@Component
public class KafkaConsumer {

    @Autowired
    AccountRepository repository;

    @Autowired
    KafkaEventProducer kafkaEventProducer;

    @KafkaListener(topics = "${kafka.topic.new}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(BidRequest bidRequest){

        kafkaEventProducer.sendProcessedRequest(newBidRequest(bidRequest));

    }

    @KafkaListener(topics = "${kafka.bid-rejected-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeRejectedBids(BidRequest bidRequest){

        rollBack(bidRequest);

    }

    void rollBack(BidRequest bidRequest) {
        if(bidRequest.getAccountbidRequestState().equals(BidRequestState.APPROVED) && bidRequest.getSagaStatus().equals(BidRequestState.ROLLBACK)) {
            Optional<AccountEntity> accountData = repository.findById(bidRequest.getUserID());
            if(accountData.isPresent()) {
                AccountEntity account = accountData.get();

                account.setBalance(account.getBalance() + bidRequest.getAmount());
                repository.save(account);
            }
        }
    }

    BidRequest newBidRequest(BidRequest bidRequest) {

        Optional<AccountEntity> accountData = repository.findById(bidRequest.getUserID());

        if (accountData.isPresent()) {
            AccountEntity account = accountData.get();
            if (account.getBalance() >= bidRequest.getAmount()) {
                account.setBalance(account.getBalance() - bidRequest.getAmount());
                bidRequest.setAccountbidRequestState(BidRequestState.APPROVED);
                repository.save(account);
                System.out.println("Account confirmed and balance reserved");
                return bidRequest;
            } else {
                bidRequest.setAccountbidRequestState(BidRequestState.REJECTED);
                bidRequest.setSource("Insufficient funds");
                return bidRequest;
            }
        }
        bidRequest.setAccountbidRequestState(BidRequestState.REJECTED);
        bidRequest.setSource("Account not found!");
        return bidRequest;
    }



}
