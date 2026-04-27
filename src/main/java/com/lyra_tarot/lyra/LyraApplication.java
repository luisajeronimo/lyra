package com.lyra_tarot.lyra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class LyraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyraApplication.class, args);
	}

}
