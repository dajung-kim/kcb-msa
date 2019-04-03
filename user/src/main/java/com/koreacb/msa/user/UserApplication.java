package com.koreacb.msa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RestController;

import com.koreacb.msa.user.biz.User;
import com.koreacb.msa.user.biz.UserRepository;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
public class UserApplication implements CommandLineRunner {
	
	@Autowired
	UserRepository userRepo;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		System.out.println(userRepo.save(new User(1, "Á¤¿µ¼ö","terun707@naver.com", 30)));
		System.out.println(userRepo.save(new User(2, "»ï·æ","sssag@naver.com", 33)));
		System.out.println(userRepo.save(new User(3, "¼¼¿ë","ss@naver.com", 20)));
	}
}

