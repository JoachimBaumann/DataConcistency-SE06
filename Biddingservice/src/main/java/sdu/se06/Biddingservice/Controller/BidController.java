package sdu.se06.Biddingservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.se06.Biddingservice.kafka.KafkaEventProducer;
import sdu.se06.auctioncommon.Model.BidRequest;


@CrossOrigin("*")
@RequestMapping("/bid/")
@RestController
public class BidController {

    @Autowired
    KafkaEventProducer kafkaEventProducer;

    @PostMapping("/newbid")
    public ResponseEntity<String> addBid(@RequestBody BidRequest bidRequest) {

        kafkaEventProducer.sendBidRequest(bidRequest);
        System.out.println(bidRequest);

        return ResponseEntity.ok("Bid recieved");

    }

}
