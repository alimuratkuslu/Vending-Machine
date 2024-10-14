package vending_machine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vending_machine.adapter.out.persistence.PostgresProductAdapter;
import vending_machine.adapter.out.persistence.ProductRepository;
import vending_machine.application.port.out.ProductPort;

@Configuration
public class ProductConfig {

    @Bean
    public ProductPort productPort(ProductRepository productRepository) {
        return new PostgresProductAdapter(productRepository);
    }
}
