package kawtar.emsi.inventoryservice;

import kawtar.emsi.inventoryservice.Repository.ProductRepository;
import kawtar.emsi.inventoryservice.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

 @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration repositoryRestConfiguration){
        return args ->{
            repositoryRestConfiguration.exposeIdsFor(Product.class);
            productRepository.saveAll(
                    List.of(
                            Product.builder().name("PC").quantity(20).price(3000).build(),
                            Product.builder().name("TV").quantity(40).price(5000).build(),
                            Product.builder().name("PHONE").quantity(50).price(9000).build()
                    )
            );
        };

 }
}
