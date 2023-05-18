package sdu.se06.Biddingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import sdu.se06.Biddingservice.kafka.KafkaEventProducer;
import sdu.se06.auctioncommon.Model.BidRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@CrossOrigin("*")
@RestController
@SpringBootApplication
@EnableKafka
public class BiddingServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(BiddingServiceApplication.class, args);
    }

}