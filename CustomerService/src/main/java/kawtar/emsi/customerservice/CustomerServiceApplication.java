package kawtar.emsi.customerservice;

import kawtar.emsi.customerservice.Repository.CustomerRepository;
import kawtar.emsi.customerservice.entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
		return args ->{
			restConfiguration.exposeIdsFor(Customer.class);
			customerRepository.saveAll(
					List.of(
							Customer.builder().name("kawtar").email("premiermail").build(),
							Customer.builder().name("kawtara").email("secondmail").build()
					)
			);
			customerRepository.findAll().forEach(System.out::println);
		};
	}

}
