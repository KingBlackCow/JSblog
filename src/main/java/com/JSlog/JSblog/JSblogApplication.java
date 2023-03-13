package com.JSlog.JSblog;

import com.JSlog.JSblog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class JSblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JSblogApplication.class, args);
	}

}
