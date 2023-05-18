package sdu.se06.Catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.se06.Catalog.Kafka.KafkaEventProducer;
import sdu.se06.auctioncommon.Model.BidRequest;

import java.util.List;
@EnableKafka
@SpringBootApplication
public class CatalogApplication {

	@Autowired
	KafkaEventProducer kafkaEventProducer;

	@Autowired
	KafkaTemplate<Integer, BidRequest> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}





}
