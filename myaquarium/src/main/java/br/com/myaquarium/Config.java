package br.com.myaquarium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class Config {

	public static void main(String[] args) {
		SpringApplication.run(Config.class, args);
	}

}
