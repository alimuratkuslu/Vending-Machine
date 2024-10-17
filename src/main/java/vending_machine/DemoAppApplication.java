package vending_machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vending_machine.config.ProductConfig;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "vending_machine.adapter.out.persistence")
@Import({
		ProductConfig.class
})
public class DemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAppApplication.class, args);
	}

}
