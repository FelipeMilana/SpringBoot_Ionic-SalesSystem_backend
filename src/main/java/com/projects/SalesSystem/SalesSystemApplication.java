package com.projects.SalesSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projects.SalesSystem.services.DBService;

@SpringBootApplication
public class SalesSystemApplication implements CommandLineRunner{

	@Autowired
	private DBService dbService;
	
	public static void main(String[] args) {
		SpringApplication.run(SalesSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dbService.instantiateDatabase();
	}

}
