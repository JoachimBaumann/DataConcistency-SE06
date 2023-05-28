package sdu.se06.Catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
@EnableKafka
@SpringBootApplication
public class CatalogApplication {


	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}





}
