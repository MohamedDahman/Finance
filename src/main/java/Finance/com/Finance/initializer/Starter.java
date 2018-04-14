package Finance.com.Finance.initializer;

import Finance.com.Finance.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;

@Configuration
public class Starter {


    @Bean
    ApplicationRunner applicationRunner(){
        return  args -> {

        };
    }
}
