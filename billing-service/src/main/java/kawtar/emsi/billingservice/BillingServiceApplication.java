package kawtar.emsi.billingservice;

import kawtar.emsi.billingservice.Service.CustomerRestClient;
import kawtar.emsi.billingservice.Service.ProductRestClient;
import kawtar.emsi.billingservice.entities.Bill;
import kawtar.emsi.billingservice.entities.ProductItem;
import kawtar.emsi.billingservice.model.Customer;
import kawtar.emsi.billingservice.model.Product;
import kawtar.emsi.billingservice.repository.BillRepository;
import kawtar.emsi.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
  @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository, CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient){
        return args->{
            Collection<Product> products= productRestClient.allProduct().getContent();
            Long customerId=1L;
            Customer customer=customerRestClient.findCustomerById(customerId);
            if(customer==null) throw new RuntimeException("Client non trouvé");
            Bill bill =new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);
            Bill savedBill= billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem= new ProductItem();
                productItem.setBill(savedBill);
                productItem.setId(product.getId());
                productItem.setQuantity(new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepository.save(productItem);
            });

        };
  }
}
