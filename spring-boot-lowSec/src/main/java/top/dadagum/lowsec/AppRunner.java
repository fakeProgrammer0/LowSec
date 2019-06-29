package top.dadagum.lowsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description TODO
 * @Author Honda
 * @Date 2019/6/28 10:27
 **/
@SpringBootApplication
public class AppRunner {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
