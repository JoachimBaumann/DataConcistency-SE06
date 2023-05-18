package sdu.se06.Biddingservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.se06.Biddingservice.kafka.KafkaEventProducer;
import sdu.se06.auctioncommon.Model.BidRequest;


@CrossOrigin("*")
@RequestMapping("/bid/")
@RestController
public class BidController {

    KafkaEventProducer kafkaEventProducer;

    @PostMapping("/newbid")
    public ResponseEntity<String> addBid(@RequestBody BidRequest bidRequest) {

        kafkaEventProducer.sendBidRequest(bidRequest);

        return ResponseEntity.ok("Bid recieved");

    }

}
