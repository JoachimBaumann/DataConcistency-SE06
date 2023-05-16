package sdu.se06.Biddingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.se06.Biddingservice.kafka.KafkaEventProducer;
import sdu.se06.auctioncommon.Model.BidRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@RestController
@SpringBootApplication
@EnableKafka
public class BiddingServiceApplication {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaEventProducer kafkaEventProducer;

    @GetMapping("/test")
    private void postBid() {
        kafkaEventProducer.sendBidRequest(new BidRequest(50,2,1.0,new Date(LocalDate.now().toEpochDay())));
    }




    public static void main(String[] args) {
        SpringApplication.run(BiddingServiceApplication.class, args);
    }

}