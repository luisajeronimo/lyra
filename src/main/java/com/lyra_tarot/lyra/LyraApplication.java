package com.lyra_tarot.lyra;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableRetry
public class LyraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyraApplication.class, args);
	}

	@PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
