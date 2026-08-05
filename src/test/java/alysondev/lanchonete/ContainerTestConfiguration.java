package alysondev.lanchonete;

import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerTestConfiguration {

    @Bean
    @ServiceConnection

    public PostgreSQLContainer<?> postgreContainer(){
        return new PostgreSQLContainer<>("postgres:16-alpine");
    }
}
