package pt.psoft.g1.psoftg1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.kafka.annotation.EnableKafka;

import java.awt.image.BufferedImage;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableKafka
public class LendingsCApp {

	public static void main(String[] args) {
		SpringApplication.run(LendingsCApp.class, args);
	}

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}
}