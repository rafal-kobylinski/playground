package rk.prov.performancetest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import rk.prov.performancetest.connectors.SshClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PerformancetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformancetestApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(DbService dbService, Reader reader) {

		return (args) -> {
			//System.out.println(dbService.getTps());
             reader.sendRequests("./input/test2.txt");
		};

	}
}
