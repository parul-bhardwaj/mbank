package mbank;

import mbank.validator.RoleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        RoleParser roleParser = run.getBean(RoleParser.class);
//        System.out.println(roleParser.isWalletUser("UserManager"));
    }
}
