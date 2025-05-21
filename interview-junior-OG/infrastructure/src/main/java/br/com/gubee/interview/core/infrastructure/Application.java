package br.com.gubee.interview.core.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.gubee.interview.core")
@EnableJpaRepositories(basePackages = "br.com.gubee.interview.core.adapters.outbound.respositories")
@EntityScan(basePackages = "br.com.gubee.interview.core.adapters.outbound.entities")
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
