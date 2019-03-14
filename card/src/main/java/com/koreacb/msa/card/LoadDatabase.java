package com.koreacb.msa.card;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CardRepository repository) {
        return args -> {
            System.out.println("Preloading "+ repository.save(new Card(10001, "NH", "credit", 500000000, 100000, "20160101")));
            System.out.println("Preloading "+ repository.save(new Card(10001, "IBK", "check", 100000, 250, "20161231")));
            System.out.println("Preloading "+ repository.save(new Card(10002, "KB", "check", 1000000000, 500000, "20171201")));
        };
    }
}
