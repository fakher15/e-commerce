package org.sid.ecomback;

import net.bytebuddy.utility.RandomString;
import org.sid.ecomback.dao.CategoryRepository;
import org.sid.ecomback.dao.ProductRepository;
import org.sid.ecomback.entities.Category;
import org.sid.ecomback.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
public class EcomBackApplication implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository ;
    @Autowired
    private ProductRepository productRepository ;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(EcomBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repositoryRestConfiguration.exposeIdsFor(Product.class,Category.class);

        categoryRepository.save(new Category("ordinateur",null,null,null));
        categoryRepository.save(new Category("smartphones",null,null,null));
        categoryRepository.save(new Category("imprimantes",null,null,null));
        Random rnd = new Random();
        categoryRepository.findAll().forEach(c->{
            for (int i = 0; i < 10; i++) {
                Product p=new Product();
                p.setName(RandomString.make(20));
                p.setCurrentPrice(100+ rnd.nextInt(10000));
                p.setAvailable(rnd.nextBoolean());
                p.setSelected(rnd.nextBoolean());
                p.setPromotion(rnd.nextBoolean());
                p.setCategory(c);
                p.setPhotoName("unknown.png");
                productRepository.save(p);

            }

        });

    }
}
