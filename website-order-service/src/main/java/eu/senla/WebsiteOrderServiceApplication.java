package eu.senla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WebsiteOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteOrderServiceApplication.class, args);
    }

//    @Bean
//    public NewTopic adviceTopic() {
//        return new NewTopic(TOPIC_NAME, 3, (short) 1);
//    }

}
